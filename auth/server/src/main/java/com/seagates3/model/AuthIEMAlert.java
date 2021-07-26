package com.seagates3.model;

import com.seagates3.s3service.IEMRestClient;
import com.seagates3.s3service.S3HttpResponse;

import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;
import com.seagates3.authserver.AuthServerConfig;

import io.netty.handler.codec.http.HttpResponseStatus;

import java.io.IOException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public
class AuthIEMAlert {

 private
 final static Logger LOGGER =
     LoggerFactory.getLogger(AuthIEMAlert.class.getName());
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
      iem_msg.put("event_id", eventCodeString);
      iem_msg.put("severity", severity);
      iem_msg.put("message_blob", message + cause);
    }
    catch (JSONException e) {
      LOGGER.error("Failed to create JSON message");
    }

    try {
      S3HttpResponse resp = new S3HttpResponse();
      resp = IEMRestClient.postRequest(iem_msg.toString());

      if(resp.getStatusCode==200){
        Logger.debug("IEM Alert send successful for this event : " + eventCodeString);
      }
      else{
        Logger.error("Failed to send IEM alert message, status : " + resp.getStatusCode());
      }
    }
    catch (Exception e) {
      LOGGER.error("Failed to send IEM alert message" + e.getMessage());
    }
  }
}
