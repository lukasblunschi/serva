package ch.serva.tools.comparators;

import java.util.Comparator;

import ch.serva.db.Domain;

public class DomainComparator implements Comparator<Domain> {

	public int compare(Domain domain1, Domain domain2) {

		// compare by domain name
		return domain1.getDomainname().compareTo(domain2.getDomainname());
	}

}
