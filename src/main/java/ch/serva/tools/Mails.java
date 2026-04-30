package ch.serva.tools;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

/**
 * Helper class to send emails.
 * 
 * @author Lukas Blunschi
 */
public class Mails {

	public static final String EMAIL_REGEX = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*$";

	private static final Logger logger = Logger.getLogger(Mails.class);

	/**
	 * Send an email.
	 * 
	 * @param props
	 *            properties to configure sending emails (mail.from, mail.user, mail.password, mail.smtp.host).
	 * @param replyToAddresses
	 *            List of reply-to addresses.
	 * @param toAddresses
	 *            List of recipient email addresses.
	 * @param subject
	 *            subject of email.
	 * @param body
	 *            body of email.
	 * @param async
	 *            true to send the message asynchronuously.
	 * @return error message or null if successfully sent.
	 */
	public static String send(Properties props, List<String> replyToAddresses, List<String> toAddresses, String subject, String body,
			boolean async) {

		// read configuration
		final String from = props.getProperty("mail.from");
		final String user = props.getProperty("mail.user");
		final String password = props.getProperty("mail.password");
		final String host = props.getProperty("mail.smtp.host");
		if (!from.matches(EMAIL_REGEX)) {
			String msg = "From address not (or badly) configured: " + from;
			logger.info(msg);
			return msg;
		}

		// create reply-to array
		final InternetAddress[] replyTos = createAddressArray(replyToAddresses);

		// create recipient array
		final InternetAddress[] Tos = createAddressArray(toAddresses);
		if (Tos.length == 0) {
			return null;
		}

		// create SMTP session and
		// create message
		Session mailSession = null;
		MimeMessage message = null;
		try {
			// create SMTP session
			Properties props2 = new Properties();
			props2.setProperty("mail.smtp.host", host);
			props2.setProperty("mail.smtp.auth", "true");
			props2.setProperty("mail.smtp.starttls.enable", "true");
			props2.setProperty("mail.smtp.port", "587");
			mailSession = Session.getInstance(props2);

			// create message
			message = new MimeMessage(mailSession);
			message.setFrom(new InternetAddress(from));
			message.setReplyTo(replyTos);
			message.setRecipients(Message.RecipientType.TO, Tos);
			message.setSentDate(new Date());
			message.setSubject(subject, "utf-8");
			message.setText(body, "utf-8");

		} catch (MessagingException me) {
			String msg = me.getMessage();
			msg += "; " + me.toString();
			Exception next = me.getNextException();
			while (next != null) {
				msg += "; " + next.getMessage();
				next = me.getNextException();
			}
			String result = "Creating message failed: " + msg;
			logger.warn(result);
			return result;
		}
		final Session finalMailSession = mailSession;
		final MimeMessage finalMessage = message;

		// send it
		if (async) {
			new Thread(new Runnable() {
				public void run() {
					sendInternal(finalMailSession, user, password, finalMessage, Tos);
				}
			}, "Mail Sender").start();

			// always result result (we do not know yet)
			return null;
		} else {
			String result = sendInternal(finalMailSession, user, password, finalMessage, Tos);
			return result;
		}
	}

	/**
	 * Send email internally.
	 * 
	 * @param finalMailSession
	 * @param user
	 * @param password
	 * @param finalMessage
	 * @param finalRecipients
	 * @return null if success, failure message otherwise.
	 */
	private static String sendInternal(Session finalMailSession, String user, String password, MimeMessage finalMessage,
			InternetAddress[] finalRecipients) {
		try {
			long start = System.currentTimeMillis();
			Transport transport = finalMailSession.getTransport("smtp");
			transport.connect(user, password);
			transport.sendMessage(finalMessage, finalRecipients);
			long duration = System.currentTimeMillis() - start;
			logger.info("Time required to send email: " + duration + "ms.");
		} catch (MessagingException me) {
			String msg = me.getMessage();
			msg += "; " + me.toString();
			Exception next = me.getNextException();
			while (next != null) {
				msg += "; " + next.getMessage();
				Exception tmp = me.getNextException();
				// break endless-loop if cause is exception itself.
				if (tmp == next) {
					break;
				}
				next = tmp;
			}
			String result = "Sending message failed: " + msg;
			logger.warn(result);
			return result;
		}
		return null;
	}

	/**
	 * Create an array of valid email addresses out of given list of strings.
	 * 
	 * @param addressesList
	 * @return
	 */
	private static InternetAddress[] createAddressArray(List<String> addressesList) {
		InternetAddress[] addressesArray = null;
		InternetAddress[] addressesArrayTmp = new InternetAddress[addressesList.size()];
		Iterator<String> iterAddresses = addressesList.iterator();
		int numValid = 0;
		while (iterAddresses.hasNext()) {
			String address = iterAddresses.next();
			if (address.matches(EMAIL_REGEX)) {
				try {
					addressesArrayTmp[numValid++] = new InternetAddress(address);
				} catch (AddressException e) {
					// ignore - should not happen
					logger.warn("Problem with email address: " + address);
				}
			} else {
				logger.warn("Ignoring invalid email address: " + address);
			}
		}
		addressesArray = new InternetAddress[numValid];
		for (int j = 0; j < numValid; j++) {
			addressesArray[j] = addressesArrayTmp[j];
		}
		return addressesArray;
	}

}
