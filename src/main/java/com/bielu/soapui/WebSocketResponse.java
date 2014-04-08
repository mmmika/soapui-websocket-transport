package com.bielu.soapui;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import com.eviware.soapui.impl.support.AbstractHttpRequestInterface;
import com.eviware.soapui.impl.wsdl.submit.transports.http.HttpResponse;
import com.eviware.soapui.impl.wsdl.submit.transports.http.SSLInfo;
import com.eviware.soapui.model.iface.Attachment;
import com.eviware.soapui.model.iface.Request;
import com.eviware.soapui.support.types.StringToStringsMap;

/**
 * @author Przemyslaw Bielicki
 */
public class WebSocketResponse implements HttpResponse {

  private String responseContent;
  private Request request;
  private final CountDownLatch contentLatch;

  public WebSocketResponse(Request request) {
    this.request = request;
    contentLatch = new CountDownLatch(1);
  }

  public Attachment[] getAttachments() {
    return null;
  }

  public Attachment[] getAttachmentsForPart(String arg0) {
    return null;
  }

  public String getContentAsString() {
    return getResponseContent();
  }

  public String getContentAsXml() {
    return getResponseContent();
  }

  public long getContentLength() {
    return getRequestContent().length();
  }

  public String getContentType() {
    return "text/xml";
  }

  public String getProperty(String arg0) {
    return null;
  }

  public String[] getPropertyNames() {
    return null;
  }

  public byte[] getRawRequestData() {
    return request.getRequestContent().getBytes();
  }

  public byte[] getRawResponseData() {
    return getResponseContent().getBytes();
  }

  public String getRequestContent() {
    return request.getRequestContent();
  }

  public StringToStringsMap getRequestHeaders() {
    return null;
  }

  public StringToStringsMap getResponseHeaders() {
    return null;
  }

  public long getTimeTaken() {
    return 0;
  }

  public long getTimestamp() {
    return 0;
  }

  public void setProperty(String arg0, String arg1) {
  }

  public void setResponseContent(String msg) {
    this.responseContent = msg;
    contentLatch.countDown();
  }
  
  public String getResponseContent() {
    try {
      contentLatch.await(1, TimeUnit.SECONDS);
      return responseContent;
    } catch (InterruptedException e) {
      throw new IllegalStateException(e);
    }
  }

  public AbstractHttpRequestInterface<?> getRequest() {
    return (AbstractHttpRequestInterface<?>) request;
  }

  public SSLInfo getSSLInfo() {
    return null;
  }

  public URL getURL() {
    try {
      return new URL(request.getEndpoint());
    } catch (MalformedURLException e) {
      throw new IllegalStateException(e);
    }
  }

  public String getMethod() {
    return "POST";
  }

  public String getHttpVersion() {
    return "1.1";
  }

  public int getStatusCode() {
    return getResponseContent() != null ? 200 : 500;
  }
}