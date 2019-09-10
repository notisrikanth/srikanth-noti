package com.example.storage.documents;

@SuppressWarnings("serial")
public class DocumentNotFoundException extends RuntimeException {
	
	public DocumentNotFoundException(String exception) {
		super(exception);
	}

}
