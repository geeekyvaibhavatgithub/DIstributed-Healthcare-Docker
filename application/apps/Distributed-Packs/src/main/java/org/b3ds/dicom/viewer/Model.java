package org.b3ds.dicom.viewer;

import java.util.List;

public class Model {
	private List<String> patientList;
	
	public Model(){
		
	}

	public List<String> getPatientList() {
		return patientList;
	}

	public void setPatientList(List<String> patientList) {
		this.patientList = patientList;
	}

	@Override
	public String toString() {
		return "Model [patientList=" + patientList + "]";
	}
	
	
}
