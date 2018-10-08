package com.packageIxia.sistemaControleEscala.unitTests;

import java.beans.PropertyEditor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import javax.servlet.http.HttpUpgradeHandler;
import javax.servlet.http.Part;

import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.core.env.Environment;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

@SuppressWarnings("deprecation")
public class ApplicationTestBase {

	public static Environment environment = new Environment() {
		
		@Override
		public String resolveRequiredPlaceholders(String text) throws IllegalArgumentException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public String resolvePlaceholders(String text) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public <T> T getRequiredProperty(String key, Class<T> targetType) throws IllegalStateException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public String getRequiredProperty(String key) throws IllegalStateException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public <T> T getProperty(String key, Class<T> targetType, T defaultValue) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public <T> T getProperty(String key, Class<T> targetType) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public String getProperty(String key, String defaultValue) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public String getProperty(String key) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public boolean containsProperty(String key) {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public String[] getDefaultProfiles() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public String[] getActiveProfiles() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public boolean acceptsProfiles(String... profiles) {
			// TODO Auto-generated method stub
			return false;
		}
	};
	
	public static HttpServletResponse response = new HttpServletResponse() {
		
		@Override
		public void setLocale(Locale loc) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void setContentType(String type) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void setContentLengthLong(long length) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void setContentLength(int len) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void setCharacterEncoding(String charset) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void setBufferSize(int size) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void resetBuffer() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void reset() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public boolean isCommitted() {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public PrintWriter getWriter() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public ServletOutputStream getOutputStream() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Locale getLocale() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public String getContentType() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public String getCharacterEncoding() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public int getBufferSize() {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public void flushBuffer() throws IOException {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void setStatus(int sc, String sm) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void setStatus(int sc) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void setIntHeader(String name, int value) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void setHeader(String name, String value) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void setDateHeader(String name, long date) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void sendRedirect(String location) throws IOException {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void sendError(int sc, String msg) throws IOException {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void sendError(int sc) throws IOException {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public int getStatus() {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public Collection<String> getHeaders(String name) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Collection<String> getHeaderNames() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public String getHeader(String name) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public String encodeUrl(String url) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public String encodeURL(String url) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public String encodeRedirectUrl(String url) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public String encodeRedirectURL(String url) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public boolean containsHeader(String name) {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public void addIntHeader(String name, int value) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void addHeader(String name, String value) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void addDateHeader(String name, long date) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void addCookie(Cookie cookie) {
			// TODO Auto-generated method stub
			
		}
	};
	
	public static HttpSession session = new HttpSession() {
		
		@Override
		public void setMaxInactiveInterval(int interval) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void setAttribute(String name, Object value) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void removeValue(String name) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void removeAttribute(String name) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void putValue(String name, Object value) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public boolean isNew() {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public void invalidate() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public String[] getValueNames() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Object getValue(String name) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public HttpSessionContext getSessionContext() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public ServletContext getServletContext() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public int getMaxInactiveInterval() {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public long getLastAccessedTime() {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public String getId() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public long getCreationTime() {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public Enumeration<String> getAttributeNames() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Object getAttribute(String name) {
			// TODO Auto-generated method stub
			return null;
		}
	};
	
	
	public static HttpServletRequest request = new HttpServletRequest() {
		
		@Override
		public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse)
				throws IllegalStateException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public AsyncContext startAsync() throws IllegalStateException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public void setCharacterEncoding(String env) throws UnsupportedEncodingException {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void setAttribute(String name, Object o) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void removeAttribute(String name) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public boolean isSecure() {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public boolean isAsyncSupported() {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public boolean isAsyncStarted() {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public ServletContext getServletContext() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public int getServerPort() {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public String getServerName() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public String getScheme() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public RequestDispatcher getRequestDispatcher(String path) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public int getRemotePort() {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public String getRemoteHost() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public String getRemoteAddr() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public String getRealPath(String path) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public BufferedReader getReader() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public String getProtocol() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public String[] getParameterValues(String name) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Enumeration<String> getParameterNames() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Map<String, String[]> getParameterMap() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public String getParameter(String name) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Enumeration<Locale> getLocales() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Locale getLocale() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public int getLocalPort() {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public String getLocalName() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public String getLocalAddr() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public ServletInputStream getInputStream() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public DispatcherType getDispatcherType() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public String getContentType() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public long getContentLengthLong() {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public int getContentLength() {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public String getCharacterEncoding() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Enumeration<String> getAttributeNames() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Object getAttribute(String name) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public AsyncContext getAsyncContext() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public <T extends HttpUpgradeHandler> T upgrade(Class<T> httpUpgradeHandlerClass)
				throws IOException, ServletException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public void logout() throws ServletException {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void login(String username, String password) throws ServletException {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public boolean isUserInRole(String role) {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public boolean isRequestedSessionIdValid() {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public boolean isRequestedSessionIdFromUrl() {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public boolean isRequestedSessionIdFromURL() {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public boolean isRequestedSessionIdFromCookie() {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public Principal getUserPrincipal() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public HttpSession getSession(boolean create) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public HttpSession getSession() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public String getServletPath() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public String getRequestedSessionId() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public StringBuffer getRequestURL() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public String getRequestURI() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public String getRemoteUser() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public String getQueryString() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public String getPathTranslated() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public String getPathInfo() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Collection<Part> getParts() throws IOException, ServletException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Part getPart(String name) throws IOException, ServletException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public String getMethod() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public int getIntHeader(String name) {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public Enumeration<String> getHeaders(String name) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Enumeration<String> getHeaderNames() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public String getHeader(String name) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public long getDateHeader(String name) {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public Cookie[] getCookies() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public String getContextPath() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public String getAuthType() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public String changeSessionId() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public boolean authenticate(HttpServletResponse response) throws IOException, ServletException {
			// TODO Auto-generated method stub
			return false;
		}
	};
	
	public static BindingResult result = new BindingResult() {
		
		@Override
		public void setNestedPath(String nestedPath) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void rejectValue(String field, String errorCode, Object[] errorArgs, String defaultMessage) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void rejectValue(String field, String errorCode, String defaultMessage) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void rejectValue(String field, String errorCode) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void reject(String errorCode, Object[] errorArgs, String defaultMessage) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void reject(String errorCode, String defaultMessage) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void reject(String errorCode) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void pushNestedPath(String subPath) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void popNestedPath() throws IllegalStateException {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public boolean hasGlobalErrors() {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public boolean hasFieldErrors(String field) {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public boolean hasFieldErrors() {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public boolean hasErrors() {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public String getObjectName() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public String getNestedPath() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public List<ObjectError> getGlobalErrors() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public int getGlobalErrorCount() {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public ObjectError getGlobalError() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Object getFieldValue(String field) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Class<?> getFieldType(String field) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public List<FieldError> getFieldErrors(String field) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public List<FieldError> getFieldErrors() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public int getFieldErrorCount(String field) {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public int getFieldErrorCount() {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public FieldError getFieldError(String field) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public FieldError getFieldError() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public int getErrorCount() {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public List<ObjectError> getAllErrors() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public void addAllErrors(Errors errors) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public String[] resolveMessageCodes(String errorCode, String field) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public String[] resolveMessageCodes(String errorCode) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Object getTarget() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Object getRawFieldValue(String field) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public PropertyEditorRegistry getPropertyEditorRegistry() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Map<String, Object> getModel() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public PropertyEditor findEditor(String field, Class<?> valueType) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public void addError(ObjectError error) {
			// TODO Auto-generated method stub
			
		}
	};
}
