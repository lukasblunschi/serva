package ch.serva.tools.comparators;

import java.util.Comparator;

import ch.serva.db.Service;

public class ServiceComparator implements Comparator<Service> {

	public int compare(Service service1, Service service2) {

		// compare by service name
		return service1.getServicename().compareTo(service2.getServicename());
	}

}
