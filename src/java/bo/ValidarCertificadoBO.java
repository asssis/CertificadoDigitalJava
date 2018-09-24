 
package bo;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Certificado;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.DERObject;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERPrintableString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.DERUTF8String;
import static org.bouncycastle.asn1.isismtt.ocsp.RequestedCertificate.certificate;
import org.bouncycastle.x509.extension.X509ExtensionUtil;

 
public class ValidarCertificadoBO {

    static String RESPONSAVEL = "2.16.76.1.3.2";
    static String CPF = "2.16.76.1.3.1";
    static String CNPJ = "2.16.76.1.3.3";

    public String OrganizarStr(String certStr) {
        return certStr.replace("\t", "\n");
    }

    public X509Certificate decode(String certStr) {
        try {
            CertificateFactory factory = CertificateFactory.getInstance("X.509");
            String certOrganizado = OrganizarStr(certStr);
            byte[] decoded = certOrganizado.getBytes(StandardCharsets.UTF_8);
            return (X509Certificate) factory.generateCertificate(new ByteArrayInputStream(decoded));
        } catch (IllegalArgumentException | CertificateException e) {

            return null;
        }
    }

    public Certificado PegarCertificado(String certStr) {
        X509Certificate cert = decode(certStr);        
        Certificado obj = new Certificado();
        
        Map<String, String> nome_alternativos = getInfoAdicionais(cert);
          obj.setData_inicio(cert.getNotBefore());
          obj.setData_final(cert.getNotAfter());
          obj.setSerial(cert.getSerialNumber());
          obj.setVersao(cert.getVersion());
          String cpf = nome_alternativos.get(CPF);
          String cnpj = nome_alternativos.get(CNPJ); 
          String nome = nome_alternativos.get(RESPONSAVEL); 
          
          if(cnpj != null)
          {
              obj.setCpf_cnpj(cnpj);
              obj.setTipo("cnpj");
          }
          else if(cpf != null)
          {
              obj.setCpf_cnpj(cpf.substring(8,19));
              obj.setTipo("cpf");
          }
          obj.setNome(nome);
           return null;
 

    }

    private Map<String, String> getInfoAdicionais(X509Certificate certificate) {
        Map<String, String> map = new HashMap<String, String>();
        Collection<?> alternativeNames;
        try {
            alternativeNames = X509ExtensionUtil.getSubjectAlternativeNames(certificate);

            for (Object alternativeName : alternativeNames) {
                if (alternativeName instanceof ArrayList) {
                    ArrayList<?> listOfValues = (ArrayList<?>) alternativeName;
                    Object value = listOfValues.get(1);
                    if (value instanceof DERSequence) {
                        DERSequence derSequence = (DERSequence) value;
                        DERObjectIdentifier derObjectIdentifier = (DERObjectIdentifier) derSequence.getObjectAt(0);
                        DERTaggedObject derTaggedObject = (DERTaggedObject) derSequence.getObjectAt(1);
                        DERObject derObject = derTaggedObject.getObject();

                        String valueOfTag = "";
                        if (derObject instanceof DEROctetString) {
                            DEROctetString octet = (DEROctetString) derObject;
                            valueOfTag = new String(octet.getOctets());
                        } else if (derObject instanceof DERPrintableString) {
                            DERPrintableString octet = (DERPrintableString) derObject;
                            valueOfTag = new String(octet.getOctets());
                        } else if (derObject instanceof DERUTF8String) {
                            DERUTF8String str = (DERUTF8String) derObject;
                            valueOfTag = str.getString();
                        }

                       
                        if ((valueOfTag != null) && (!"".equals(valueOfTag))) {
                           
                                map.put(derObjectIdentifier.toString(), valueOfTag); 
                        }
                    }
                }
            }
        } catch (CertificateParsingException ex) {
            Logger.getLogger(ValidarCertificadoBO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return map;
    }
}
