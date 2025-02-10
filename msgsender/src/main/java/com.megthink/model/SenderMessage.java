package model;

public class SenderMessage {
	
	private String userId;
	    private String password;
	    private String sender;
	    private String receiver;
	    private String uId;
	    private String donorOp;
	    private String cuId;
	    private String type;
	    private String route;
	    private String area;
	    private String recipientOp;
	    private String service;
	    private String comment;
	    customerDetails customerDetails;
	    numberDetails numberDetails;
	    private String requestType;
		public String getUserId() {
			return userId;
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getSender() {
			return sender;
		}
		public void setSender(String sender) {
			this.sender = sender;
		}
		public String getReceiver() {
			return receiver;
		}
		public void setReceiver(String receiver) {
			this.receiver = receiver;
		}
		public String getuId() {
			return uId;
		}
		public void setuId(String uId) {
			this.uId = uId;
		}
		public String getDonorOp() {
			return donorOp;
		}
		public void setDonorOp(String donorOp) {
			this.donorOp = donorOp;
		}
		public String getCuId() {
			return cuId;
		}
		public void setCuId(String cuId) {
			this.cuId = cuId;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getRoute() {
			return route;
		}
		public void setRoute(String route) {
			this.route = route;
		}
		public String getArea() {
			return area;
		}
		public void setArea(String area) {
			this.area = area;
		}
		public String getRecipientOp() {
			return recipientOp;
		}
		public void setRecipientOp(String recipientOp) {
			this.recipientOp = recipientOp;
		}
		public String getService() {
			return service;
		}
		public void setService(String service) {
			this.service = service;
		}
		public String getComment() {
			return comment;
		}
		public void setComment(String comment) {
			this.comment = comment;
		}
		public customerDetails getCustomerDetails() {
			return customerDetails;
		}
		public void setCustomerDetails(customerDetails customerDetails) {
			this.customerDetails = customerDetails;
		}
		public numberDetails getNumberDetails() {
			return numberDetails;
		}
		public void setNumberDetails(numberDetails numberDetails) {
			this.numberDetails = numberDetails;
		}
		public String getRequestType() {
			return requestType;
		}
		public void setRequestType(String requestType) {
			this.requestType = requestType;
		}
	    
}

class customerDetails {
	       private String accountId;
	        private String name;
	       private String address;
	        private String idProof;
	        private String idProofName;
	        private String alternateNumber;
	        private String email;
	        private String attachments;
			public String getAccountId() {
				return accountId;
			}
			public void setAccountId(String accountId) {
				this.accountId = accountId;
			}
			public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}
			public String getAddress() {
				return address;
			}
			public void setAddress(String address) {
				this.address = address;
			}
			public String getIdProof() {
				return idProof;
			}
			public void setIdProof(String idProof) {
				this.idProof = idProof;
			}
			public String getIdProofName() {
				return idProofName;
			}
			public void setIdProofName(String idProofName) {
				this.idProofName = idProofName;
			}
			public String getAlternateNumber() {
				return alternateNumber;
			}
			public void setAlternateNumber(String alternateNumber) {
				this.alternateNumber = alternateNumber;
			}
			public String getEmail() {
				return email;
			}
			public void setEmail(String email) {
				this.email = email;
			}
			public String getAttachments() {
				return attachments;
			}
			public void setAttachments(String attachments) {
				this.attachments = attachments;
			}
	        
	        
	        
	            }


class numberDetails{ 
	       
	           private String msisdn;
	           private String pincode;
			public String getMsisdn() {
				return msisdn;
			}
			public void setMsisdn(String msisdn) {
				this.msisdn = msisdn;
			}
			public String getPincode() {
				return pincode;
			}
			public void setPincode(String pincode) {
				this.pincode = pincode;
			}
	      
	    

}
