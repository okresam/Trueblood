package fer.progi.illidimusdigitus.trueblood.service.impl;

import fer.progi.illidimusdigitus.trueblood.model.Blood;
import fer.progi.illidimusdigitus.trueblood.model.Donation;
import fer.progi.illidimusdigitus.trueblood.model.User;
import fer.progi.illidimusdigitus.trueblood.model.util.RoleName;
import fer.progi.illidimusdigitus.trueblood.service.EmailService;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfDocument;
import com.lowagie.text.pdf.PdfWriter;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;


@AllArgsConstructor
@Service
@EnableAsync
public class EmailSender implements EmailService {

    @Autowired
    private JavaMailSender mailSender;


    @Override
    @Async
    public void send(String to, String name, String username ,String link) {
        try {
        	
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setTo(to);
            helper.setSubject("Aktiviraj račun");
            helper.setText(buildConfirmation(name, username,link), true);
            helper.setFrom("noreply.trueblood@gmail.com");
            mailSender.send(mimeMessage);

        } catch (MessagingException exc){
            //exception wrapping
            throw new IllegalStateException("failed to send email");
        }
    }

    public String buildConfirmation(String name, String username, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Aktivacija računa</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Dobar dan " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Hvala na registraciji. Pritisnite link ispod kako bi aktivirali račun: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Aktiviraj</a> </p></blockquote>\n  <p>Vaš donorID je " + username + ".\n</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }

	@Override
	@Async
	public void sendgeneratedPDF(Donation donation, User user)  {
		ByteArrayOutputStream outputStream = null;
		try {
			
	        MimeBodyPart textBodyPart = new MimeBodyPart();
	        textBodyPart.setText(buildForPDF(user));

	        outputStream = new ByteArrayOutputStream();
	        try {
				writePDF(outputStream, donation, user);
			} catch (Exception e) {
				
				e.printStackTrace(); 
			}
	        byte[] bytes = outputStream.toByteArray();
	        //byte[] bytes = PdfWriter.getISOBytes("Potvrda o doniranju krvi");;
	        
	        
	        DataSource dataSource = new ByteArrayDataSource(bytes, "application/pdf");
	        MimeBodyPart pdfBodyPart = new MimeBodyPart();
	        pdfBodyPart.setDataHandler(new DataHandler(dataSource));
	        pdfBodyPart.setFileName("Potvrda.pdf");

	        MimeMultipart mimeMultipart = new MimeMultipart();
	        mimeMultipart.addBodyPart(textBodyPart);
	        mimeMultipart.addBodyPart(pdfBodyPart);

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setTo(user.getEmail());
            helper.setSubject("Potvrda o darivanju krvi");
            helper.setFrom("noreply.trueblood@gmail.com");
            mimeMessage.setContent(mimeMultipart);
            
            mailSender.send(mimeMessage);

        } catch (MessagingException exc){
            //exception wrapping
            throw new IllegalStateException("failed to send email");
        }
		System.out.println("POSLAN PDF");
		
	}

	
	private String buildForPDF(User user) {
		 return 
		 "Poštovani/a " + user.getName() +" " + user.getSurname() + ",\n" +
		 "hvala Vam na doniranju krvi, u privitku se nalazi potvrda o doniranju krvi.\n\n" +
		 "Vaš TrueBlood tim!";
		
	}

	public void writePDF(OutputStream ost, Donation donation, User user) throws Exception {
		Document document = new Document();
		PdfWriter.getInstance(document, ost);
	    document.open();
	    document.addTitle("Potvrda o doniranju krvi");
	    
	    Font font1 = FontFactory.getFont(FontFactory.COURIER, 27, Font.BOLD);
	    Font font2 = FontFactory.getFont(FontFactory.COURIER, 18);
	    Font font3 = FontFactory.getFont(FontFactory.COURIER, 20, Font.BOLD);
	   

	    Paragraph paragraph1 = new Paragraph();
	    paragraph1.setAlignment(com.lowagie.text.Element.ALIGN_CENTER);
	    Chunk chunk1 = new Chunk("Potvrda o doniranju krvi\n\n\n\n\n\n\n\n", font1);
	    
	    String rawString = "Potvrdjujemo da je ";
	    ByteBuffer buffer = StandardCharsets.UTF_8.encode(rawString); 
	    String encoded = StandardCharsets.UTF_8.decode(buffer).toString();
	    Chunk chunk2 = new Chunk(encoded, font2);
	   
	    rawString = user.getName() + " " +  user.getSurname();
	    buffer = StandardCharsets.UTF_8.encode(rawString); 
	    encoded = StandardCharsets.UTF_8.decode(buffer).toString();
	    Chunk chunk3 = new Chunk(encoded, font3);
	    
	    rawString = " uspješno \n\n obavio doniranje krvi ";
	    buffer = StandardCharsets.UTF_8.encode(rawString); 
	    encoded = StandardCharsets.UTF_8.decode(buffer).toString();
	    Chunk chunk4 = new Chunk(encoded, font2);
	    
	    Chunk chunk5 = new Chunk("dana\n\n", font2);
	    
	    
	    Chunk chunk6 = new Chunk(donation.getDate().toString(), font3);
	    Chunk chunk7 = new Chunk(" na lokaciji ", font2);
	    
	    rawString = new String(donation.getDonationPlace() + ".\n\n\n\n");
	    buffer = StandardCharsets.UTF_8.encode(rawString); 
	    encoded = StandardCharsets.UTF_8.decode(buffer).toString();
	    Chunk chunk8 = new Chunk(encoded, font3);
	    
	    paragraph1.add(chunk1);
	    paragraph1.add(chunk2);
	    paragraph1.add(chunk3);
	    paragraph1.add(chunk4);
	    paragraph1.add(chunk5);
	    paragraph1.add(chunk6);
	    paragraph1.add(chunk7);
	    paragraph1.add(chunk8);
	    
	    
	    Paragraph paragraph2 = new Paragraph();
	    paragraph2.setAlignment(com.lowagie.text.Element.ALIGN_RIGHT);
	    Chunk chunk9 = new Chunk("Banka krvi TrueBlood", font3);
	    chunk9.setBackground(Color.lightGray, 5, 5, 5, 5);
	    paragraph2.add(chunk9);
	    
	    document.add(paragraph1);
	    document.add(paragraph2);
	    document.close();
	}
	
	@Override
	@Async
	public void notificationLower(Blood blood, List<User> users) {
		try {
            for(User user : users) {
            	
            	MimeMessage mimeMessage = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
                helper.setTo(user.getEmail());
                
                if(user.getRole().getName().equals(RoleName.DONOR)) {
                	helper.setSubject("Poziv na doniranje krvi");
                    helper.setText(buildPoziv(user), true);
                    System.out.println(buildPoziv(user));
                }else {
                	helper.setSubject("Notifikacija o granicama");
                    helper.setText(buildNotifLower(user, blood), true);
                    System.out.println(buildNotifLower(user, blood));
                }
                
                helper.setFrom("noreply.trueblood@gmail.com");
                mailSender.send(mimeMessage);
            }
			

        } catch (MessagingException exc){
            //exception wrapping
            throw new IllegalStateException("failed to send email");
        }
	}

	@Override
	@Async
	public void notificationUpper(Blood blood, List<User> users) {
		try {
			for(User user : users) {
            	MimeMessage mimeMessage = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
                helper.setTo(user.getEmail());
                helper.setSubject("Notifikacija o granicama");
                helper.setText(buildNotifUpper(user, blood), true);
                helper.setFrom("noreply.trueblood@gmail.com");
                mailSender.send(mimeMessage);
                System.out.println(buildNotifUpper(user, blood));
            }
        } catch (MessagingException exc){
            //exception wrapping
            throw new IllegalStateException("failed to send email");
        }
	}
	
	private String buildNotifLower(User user, Blood blood) {
		 return 
				 "Poštovani/a " + user.getName() +" " + user.getSurname() + ",\n" +
           		 "zalihe krvne grupe " + blood.getName() + " pale su ispod optimalne granice.\n\n" +
        		 "Vaš TrueBlood tim!";
		
	}
	private String buildNotifUpper(User user, Blood blood) {
		 return 
				 "Poštovani/a " + user.getName() +" " + user.getSurname() + ",\n" +
           		 "zalihe krvne grupe " + blood.getName().toString() + " porasle su iznad optimalne granice.\n\n" +
        		 "Vaš TrueBlood tim!";
		
	}
	private String buildPoziv(User user) {
		 return 
				 "Poštovani/a " + user.getName() +" " + user.getSurname() + ",\n" +
           		 "zalihe Vaše krvi pale su ispod optimalne granice pa Vas pozivamo na doniranje krvi. \n\n" +
        		 "Vaš TrueBlood tim!";
		
	}

	@Override
	@Async
	public void sendPoziv(Set<User> allUsers) {
		 try {
			 for(User user : allUsers) {
	         	MimeMessage mimeMessage = mailSender.createMimeMessage();
	            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
	            helper.setTo(user.getEmail());
	            helper.setSubject("Poziv na doniranje");
	            helper.setText(buildPozivNakon(user), true);
	            helper.setFrom("noreply.trueblood@gmail.com");
	            mailSender.send(mimeMessage);
	          
			 	}
		 	
	        } catch (MessagingException exc){
	            //exception wrapping
	            throw new IllegalStateException("failed to send email");
	        }
		
	}

	private String buildPozivNakon(User user) {
		return 
				 "Poštovani/a " + user.getName() +" " + user.getSurname() + ",\n" +
          		 "prošao je period od zadnje donacije nakon kojeg možete ponovno donirati, stoga Vas pozivamo na doniranje krvi. \n\n" +
          		 "Vaš TrueBlood tim!";
	}
}

