package ch.serva.export;

import java.util.Date;
import java.util.Properties;

import ch.serva.db.Booking;
import ch.serva.db.Domain;
import ch.serva.db.Service;
import ch.serva.db.User;
import ch.serva.localization.Dictionary;
import ch.serva.tools.Dates;
import ch.serva.tools.Doubles;
import ch.serva.tools.Props;

/**
 * PDF invoice export.
 * 
 * @author Lukas Blunschi
 * 
 */
public class PdfInvoice implements PdfExport {

	public static final String NAME = "pdfInvoice";

	private final Domain domain;

	public PdfInvoice(Domain domain) {
		this.domain = domain;
	}

	public String getXslFilename() {
		return "pdfInvoice.xsl";
	}

	public void appendXml(StringBuffer xml, Dictionary dict) {

		// properties
		Properties props = Props.load(this.getClass());

		// create XML
		xml.append("<invoice>\n");

		// company
		String companyName = props.getProperty("company.name");
		User userCompany = domain.getHostingcontact();
		xml.append("<company>\n");
		xml.append("<companyname>" + companyName + "</companyname>\n");
		xml.append("<name>" + userCompany.getFirstname() + " " + userCompany.getLastname() + "</name>\n");
		xml.append("<address>" + userCompany.getStreet() + "</address>\n");
		xml.append("<location>" + userCompany.getLocation() + "</location>\n");
		xml.append("<email>" + userCompany.getEmail() + "</email>\n");
		xml.append("<phone>" + userCompany.getPhone() + "</phone>\n");
		xml.append("</company>\n");

		// customer
		User userCustomer = domain.getBillingcontact();
		xml.append("<customer>\n");
		xml.append("<name>" + userCustomer.getFirstname() + " " + userCustomer.getLastname() + "</name>\n");
		xml.append("<address>" + userCustomer.getStreet() + "</address>\n");
		xml.append("<location>" + userCustomer.getLocation() + "</location>\n");
		xml.append("</customer>\n");

		// date
		xml.append("<date>\n");
		xml.append("<datestr>" + Dates.dateFormat.format(new Date()) + "</datestr>\n");
		xml.append("</date>\n");

		// content
		xml.append("<content>\n");
		xml.append("<title>" + dict.invoiceFor() + " " + domain.getDomainname() + "</title>\n");
		double sum = 0.0;
		for (Booking booking : domain.getBookings()) {
			Service service = booking.getService();
			String item = service.getServicename();
			double price = service.getPrice();
			double openCost = 0.0;
			if (price > 0.0) {
				Date openFrom = booking.getOpenFrom();
				Date openTo = booking.getOpenTo();
				item += " " + Dates.dateFormat.format(openFrom) + " - " + Dates.dateFormat.format(openTo);
				double totalCost = booking.getTotalCost();
				double payed = booking.getPayed();
				openCost = totalCost - payed;
				sum += openCost;
			}
			xml.append("<service>\n");
			xml.append("<item>" + item + "</item>\n");
			xml.append("<price>" + Doubles.formatter.format(openCost) + "</price>\n");
			xml.append("</service>\n");
		}
		xml.append("<total>" + dict.totalCost() + "</total>\n");
		xml.append("<sum>" + Doubles.formatter.format(sum) + "</sum>\n");
		xml.append("</content>\n");

		// account
		String companyAccount = props.getProperty("company.account");
		if (companyAccount == null) {
			companyAccount = "";
		}
		String[] lines = companyAccount.split(";");
		xml.append("<account>\n");
		xml.append("<title>" + dict.accountDetails() + "</title>\n");
		for (String line : lines) {
			xml.append("<line><info>" + line.trim() + "</info></line>\n");
		}
		xml.append("</account>\n");

		// finish XML
		xml.append("</invoice>\n");
	}

}
