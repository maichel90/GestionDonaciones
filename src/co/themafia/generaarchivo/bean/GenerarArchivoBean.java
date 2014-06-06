package co.themafia.generaarchivo.bean;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import co.themafia.generaarchivo.dto.GeneradorArchivosDetalleInDTO;
import co.themafia.generaarchivo.dto.GeneradorArchivosInDTO;

public class GenerarArchivoBean {

	public static String archivo=System.getProperty("user.home")+"/certificadoDonacion";
	
	public static String encabezado1 = "El señor (ra) ";
	public static String encabezado2 = ", identificado con ";
	public static String encabezado3 = " número ";
	public static String encabezado4 = " a realizado la (s) siguiente (s) donación (es): ";
	public static String encabezado5 = "Nombre campaña";
	public static String encabezado6 = "Fecha campaña";
	public static String encabezado7 = "Valor donación";
	public static String encabezado8 = "Forma de pago";
	public static String encabezado9 = "Se expide en Bogotá D.C. a los ";
	public static String encabezado11 = " días del mes de ";
	public static String encabezado12 = " de ";
	public static String encabezado13 = "Cordialmente; ";
	
	
	public Document generarArchivo(GeneradorArchivosInDTO inDTO) throws MalformedURLException, IOException {

		Document documento = new Document(PageSize.LETTER, 80, 80, 75, 75);

		PdfWriter writer = null;

		try {
			// Obtenemos la instancia del archivo a utilizar
			String rutaArchivo = archivo.concat("_").concat(inDTO.getNumeroIdentificacion().concat(".pdf"));
			writer = PdfWriter.getInstance(documento, new FileOutputStream(rutaArchivo));
		} catch (FileNotFoundException | DocumentException ex) {
			ex.getMessage();
		}
		// Agregamos un titulo al archivo
		documento.addTitle("Certificado de donacion");

		// Agregamos el autor del archivo
		documento.addAuthor("Admin");

		// Abrimos el documento para edición
		documento.open();
		try {
			
		Image imagenLogo = Image.getInstance("image/backgroundcampfinal1.jpg");
	      //Alineamos la imagen al centro
	      imagenLogo.setAlignment(Image.ALIGN_RIGHT);
	      //Escalamos la imagen al 50%
	      imagenLogo.scalePercent(10);
	      //Agregamos la imagen al documento
	      documento.add(imagenLogo);


		// Declaramos un texto como Paragraph
		// Le podemos dar formado como alineación, tamaño y color a la fuente.
		Paragraph parrafo = new Paragraph();
		parrafo.setAlignment(Paragraph.ALIGN_CENTER);
		parrafo.setFont(FontFactory.getFont("Sans", 20, Font.BOLD,
				BaseColor.BLACK));
		parrafo.add("BPO");
		
		Paragraph parrafo0 = new Paragraph();
		parrafo0.setAlignment(Paragraph.ALIGN_CENTER);
		parrafo0.setFont(FontFactory.getFont("Sans", 28, Font.BOLD,
				BaseColor.BLACK));
		parrafo0.add("CERTIFICA");

			documento.add(new Paragraph(" "));
			documento.add(new Paragraph(" "));
			
			// Agregamos el texto al documento
			documento.add(parrafo);
			

			documento.add(new Paragraph(" "));
			documento.add(new Paragraph(" "));
			documento.add(new Paragraph(" "));
			documento.add(new Paragraph(" "));
			
			documento.add(parrafo0);

			// Agregamos un salto de linea
			documento.add(new Paragraph(" "));

			documento.add(new Paragraph(" "));
			documento.add(new Paragraph(" "));
			documento.add(new Paragraph(" "));

			Paragraph parrafo1 = new Paragraph();

			parrafo1.setAlignment(Paragraph.ALIGN_JUSTIFIED);
			parrafo1.setFont(FontFactory.getFont("Sans", 12, Font.NORMAL,
					BaseColor.BLACK));
			parrafo1.add(encabezado1+inDTO.getNombreDonante()+encabezado2+inDTO.getTipoDocumento()+encabezado3+inDTO.getNumeroIdentificacion()
					+encabezado4);

			documento.add(parrafo1);
			
			PdfPTable tabla = new PdfPTable(4);
			
			Paragraph columna1 = new Paragraph();

			columna1.setAlignment(Paragraph.ALIGN_CENTER);
			columna1.setFont(FontFactory.getFont("Sans", 12, Font.BOLD,
					BaseColor.BLACK));
			columna1.add(encabezado5);

			tabla.addCell(columna1);
			
			Paragraph columna2 = new Paragraph();

			columna2.setAlignment(Paragraph.ALIGN_CENTER);
			columna2.setFont(FontFactory.getFont("Sans", 12, Font.BOLD,
					BaseColor.BLACK));
			columna2.add(encabezado6);

			tabla.addCell(columna2);
			
			Paragraph columna3 = new Paragraph();

			columna3.setAlignment(Paragraph.ALIGN_CENTER);
			columna3.setFont(FontFactory.getFont("Sans", 12, Font.BOLD,
					BaseColor.BLACK));
			columna3.add(encabezado7);

			documento.add(new Paragraph(" "));
			documento.add(new Paragraph(" "));
			
			tabla.addCell(columna3);
			
			Paragraph columna4 = new Paragraph();

			columna4.setAlignment(Paragraph.ALIGN_CENTER);
			columna4.setFont(FontFactory.getFont("Sans", 12, Font.BOLD,
					BaseColor.BLACK));
			columna4.add(encabezado8);

			tabla.addCell(columna4);
			
			for(GeneradorArchivosDetalleInDTO detalle : inDTO.getDatosDonacion()){
				tabla.addCell(detalle.getNombreCampana());
				tabla.addCell(detalle.getFechaDonacion());
				tabla.addCell(detalle.getValorCampana());
				tabla.addCell(detalle.getFormaPago());
				
			}
			
			documento.add(tabla);
			
			documento.add(new Paragraph(" "));
			
			Paragraph parrafo2 = new Paragraph();
			parrafo2.setAlignment(Paragraph.ALIGN_JUSTIFIED);
			parrafo2.setFont(FontFactory.getFont("Sans", 12, Font.NORMAL,
					BaseColor.BLACK));
			
			Calendar fecha = new GregorianCalendar();
			
			int anyo = fecha.get(Calendar.YEAR);
			int mesInt = fecha.get(Calendar.MONTH)+1;
		    String[] mesesString = {"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
		    String mesString = mesesString[mesInt-1];
	        int dia = fecha.get(Calendar.DAY_OF_MONTH);
	        
			parrafo2.add(encabezado9+dia+encabezado11+mesString+encabezado12+anyo+".");

			documento.add(parrafo2);

			documento.add(new Paragraph(" "));
			documento.add(new Paragraph(" "));
			
			Paragraph parrafoCordial = new Paragraph();
			parrafoCordial.setAlignment(Paragraph.ALIGN_JUSTIFIED);
			parrafoCordial.setFont(FontFactory.getFont("Sans", 12, Font.NORMAL,
					BaseColor.BLACK));
			parrafoCordial.add(encabezado13);

			documento.add(parrafoCordial);
			
			documento.add(new Paragraph(" "));
			documento.add(new Paragraph(" "));
			
			Image imagen = Image.getInstance("image/images.jpg");
		      //Alineamos la imagen al centro
		      imagen.setAlignment(Image.ALIGN_LEFT);
		      //Escalamos la imagen al 50%
		      imagen.scalePercent(30);
		      //Agregamos la imagen al documento
		      documento.add(imagen);
			
			Paragraph parrafo4 = new Paragraph();
			parrafo4.setAlignment(Paragraph.ALIGN_JUSTIFIED);
			parrafo4.setFont(FontFactory.getFont("Sans", 12, Font.NORMAL,
					BaseColor.BLACK));
			parrafo4.add("Gabriel Medellín");
			
			documento.add(parrafo4);
			
		} catch (DocumentException ex) {
			ex.getMessage();
		}

		documento.close(); // Cerramos el documento
		writer.close(); // Cerramos writer

		return documento;
	}
	
	public void abrirArchivo(GeneradorArchivosInDTO generadorArchivosInDTO){
		GeneradorArchivosInDTO inDTO = new GeneradorArchivosInDTO();
		inDTO.setNombreDonante(generadorArchivosInDTO.getNombreDonante());
		inDTO.setTipoDocumento(generadorArchivosInDTO.getTipoDocumento());
		inDTO.setNumeroIdentificacion(generadorArchivosInDTO.getNumeroIdentificacion());
		inDTO.setCorreo(generadorArchivosInDTO.getCorreo());
		List<GeneradorArchivosDetalleInDTO> detalle = new ArrayList<GeneradorArchivosDetalleInDTO>();
		
		for(GeneradorArchivosDetalleInDTO detalleList : generadorArchivosInDTO.getDatosDonacion()){
		GeneradorArchivosDetalleInDTO inDetalle = new GeneradorArchivosDetalleInDTO();
		
		inDetalle.setFechaDonacion(detalleList.getFechaDonacion());
		inDetalle.setFormaPago(detalleList.getFormaPago());
		inDetalle.setNombreCampana(detalleList.getNombreCampana());
		inDetalle.setValorCampana("$"+detalleList.getValorCampana());
		detalle.add(inDetalle);
		inDTO.setDatosDonacion(detalle);
		}
		try {
			this.generarArchivo(inDTO );
			
			String ruta = archivo.concat("_").concat(inDTO.getNumeroIdentificacion().concat(".pdf"));
			
//			File file = new File(ruta);
			
//			Desktop.getDesktop().open(file);
			
			this.enviarCorreo(ruta, inDTO.getCorreo());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void enviarCorreo (String ruta, String correo) throws MessagingException{
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.setProperty("mail.smtp.starttls.enable", "true");
		props.setProperty("mail.smtp.port","587");
		props.setProperty("mail.smtp.user", "correoBPO@gmail.com");
		props.setProperty("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(props, null);
		session.setDebug(true);
		
		BodyPart texto = new MimeBodyPart();
		texto.setText("Un cordial saludo, se envia archivo adjunto el cual contiene el certificado de donación solicitado.");
		
		BodyPart adjunto = new MimeBodyPart();
		adjunto.setDataHandler(new DataHandler(new FileDataSource(ruta)));
		adjunto.setFileName("Certificado donación.pdf");
		
		MimeMultipart multiParte = new MimeMultipart();

		multiParte.addBodyPart(texto);
		multiParte.addBodyPart(adjunto);
		
		MimeMessage message = new MimeMessage(session);

		// Se rellena el From
		message.setFrom(new InternetAddress(correo));

		// Se rellenan los destinatarios
		message.addRecipient(Message.RecipientType.TO, new InternetAddress("davidmpv01@gmail.com"));

		// Se rellena el subject
		message.setSubject("Certificado donación");

		// Se mete el texto y la foto adjunta.
		message.setContent(multiParte);
		
		Transport t = session.getTransport("smtp");
		t.connect("correoBPO@gmail.com","correobpo2014");
		t.sendMessage(message,message.getAllRecipients());
		t.close();
	}

	public static void main(String args []){
		GenerarArchivoBean gab = new GenerarArchivoBean();
		
		GeneradorArchivosInDTO generadorArchivosInDTO = new GeneradorArchivosInDTO();
		GeneradorArchivosDetalleInDTO inDetalle = new GeneradorArchivosDetalleInDTO();
		List<GeneradorArchivosDetalleInDTO> listDetalle = new ArrayList<GeneradorArchivosDetalleInDTO>();
		generadorArchivosInDTO.setCorreo("davidmpv01@gmail.com");
		generadorArchivosInDTO.setNombreDonante("David Poveda");
		generadorArchivosInDTO.setNumeroIdentificacion("80187069");
		generadorArchivosInDTO.setTipoDocumento("Cedula de ciudadania");
		inDetalle.setFechaDonacion("25/04/2014");
		inDetalle.setFormaPago("Efectivo");
		inDetalle.setNombreCampana("Teleton");
		inDetalle.setValorCampana("600,000.oo");
		listDetalle.add(inDetalle);
		generadorArchivosInDTO.setDatosDonacion(listDetalle);
		gab.abrirArchivo(generadorArchivosInDTO );
	}
	
}
