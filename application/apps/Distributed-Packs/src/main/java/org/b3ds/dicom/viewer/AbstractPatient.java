package org.b3ds.dicom.viewer;

import java.util.List;
import java.util.Map;

public class AbstractPatient implements IfacePatient{

	@Override
	public String getAllPatients() {
		return null;
	}

	@Override
	public String searchByPatientName(String patientName) {
		return null;
	}
	
	public List<Map<?, ?>> getSinglePatientDetails(String paramName, String paramValue, List<String> fields)
	{
		return null;
	}
	
}
