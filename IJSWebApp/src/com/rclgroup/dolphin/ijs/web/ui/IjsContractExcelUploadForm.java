package com.rclgroup.dolphin.ijs.web.ui;


import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

public class IjsContractExcelUploadForm extends ActionForm{

	private FormFile file;

	public FormFile getFile() {
        System.out.println("File get in Bean.......######################## ");
		return file;
	}

	public void setFile(FormFile file) {
	    System.out.println("File set in Bean.......######################## "+file);
		this.file = file;
	}

//	@Override
//	public ActionErrors validate(ActionMapping mapping,
//	   HttpServletRequest request) {
//
//	    ActionErrors errors = new ActionErrors();
//
//	    if( getFile().getFileSize()== 0){
//	       errors.add("common.file.err",
//	    	new ActionMessage("error.common.file.required"));
//	       return errors;
//	    }
//
//	    //only allow textfile to upload
//	    if(!"text/plain".equals(getFile().getContentType())){
//	        errors.add("common.file.err.ext",
//	    	 new ActionMessage("error.common.file.textfile.only"));
//	        return errors;
//	    }
//
//            //file size cant larger than 10kb
//	    System.out.println(getFile().getFileSize());
//	    if(getFile().getFileSize() > 10240){ //10kb
//	       errors.add("common.file.err.size",
//		    new ActionMessage("error.common.file.size.limit", 10240));
//	       return errors;
//	    }
//
//	    return errors;
//	}
}

