package com.seagates3.model;

public
class AuthIEMAlert {
  // TODO Base template class for IEM alerts
  public
   static final String LDAP_EX = "0040010001";
  public
   static final String UTF8_UNAVAILABLE = "0040020001";
  public
   static final String HMACSHA256_UNAVAILABLE = "0040020002";
  public
   static final String HMACSHA1_UNAVAILABLE = "0040020003";
  public
   static final String SHA256_UNAVAILABLE = "0040020004";
  public
   static final String UNPARSABLE_DATE = "0040030001";
  public
   static final String CLASS_NOT_FOUND_EX = "0040040001";
  public
   static final String NO_SUCH_METHOD_EX = "0040040002";
  public
   static final String XML_SCHEMA_VALIDATION_ERROR = "0040050001";
  public
   static final String INVALID_REST_URI_ERROR = "0040060001";

  // create json object
  private
  static void generateIemAlert(String severity, String eventCodeString,
                                  String message, String cause) {
    JSONObject iem_msg = new JSONObject();
    try {
      iem_msg.put("component", "S3");
      iem_msg.put("source", "S");
      iem_msg.put("module", "AuthServer");
      iem_msg.put("event_id", "1500");
      iem_msg.put("severity", severity);
      iem_msg.put("message_blob", message + cause);
    }
    catch (JSONException e) {
      LOGGER.error("Failed to create JSON message");
    }
    
    try{
      resp = IEMRestClient.postRequest(iem_msg.toString());
    }
    catch(Exception e){
      resp.setHttpCode(HttpResponseStatus.internalServerError.code());
      resp.setHttpStatusMessage(e.getMessage());
    }
  }
}
