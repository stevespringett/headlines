## SourceClear - Header Security
### A collection of security related response headers designed to help increase security for servlet based apps.
===

##Overview
Our HeaderSecurity project is a Java project inspired by Twitter's [SecureHeaders](https://github.com/twitter/secureheaders).  We have a Java implementation which
provides similar functionality to Twitter's Ruby implementation.  When combined with our [CSP project](https://github.com/sourceclear/csp) we implement the same
security headers as Twitter's.

##Functionality
Here's what our SecureHeader implementation covers:

###1) [HTTP Strict Transport Security (HSTS)](https://tools.ietf.org/html/rfc6797)
###2) [X-Frame-Options](https://tools.ietf.org/html/draft-ietf-websec-x-frame-options-00)
###3) [Microsoft's XSS Filter](http://msdn.microsoft.com/en-us/library/dd565647(v=vs.85).aspx)
###4) [X-Content-Type Options](http://msdn.microsoft.com/en-us/library/ie/gg622941(v=vs.85).aspx)

##Installation
Maven users cant simply use the following dependency:

```xml
<dependency>
  <groupId>com.sourceclear</groupId>
  <artifactId>headers-security</artifactId>
  <version>0.1.0-SNAPSHOT</version>    
</dependency>
```
Note: SourceClear - HeaderSecurity is not yet in Maven Central, so users of the library will have to download and build the Maven project prior to including it.

## Setting things up
Once you have dependencies taken care of, setting up HeaderSecurity can be done in two steps:

### 1) Set up the servlet filter
Edit your web.xml file with the following entry:

```xml
<filter>
  <filter-name>headersecurity</filter-name>
  <filter-class>com.sourceclear.headersecurity.HeaderSecurityFilter</filter-class>
</filter>

<filter-mapping>
  <filter-name>headersecurity</filter-name>
  <url-pattern>*</url-pattern>
</filter-mapping>
```

### 2) Set up a config file (optional).
Without a config file, HeaderSecurity will use a set of sensible default options.  If you want some of the features turned off or wish to fine tune
others you'll want a config file.  Here is a complete config which uses the default settings:

```json

{
  "xContentType": {
    "enabled":true 
  },

  "xFrameOptions": {
    "enabled":true,
    "value":"DENY"
  },

  "xssProtection": {
    "enabled":true
  },

  "hstsConfig": {
    "enabled":true,
    "includeSubdomains":true,
    "maxAge":31536000
  },

  "proxyHeader":"X-Forwarded-Proto"
}

```

This file should be saved into WEB-INF/headerSecurity.conf.

Most of the options should be self-explanatory.  The hstsConfig->maxAge option is the length in seconds the HSTS directive should be followed.  The spec
recommends a length of 1 to 2 years.  The defualt is 1 year.  The 'proxyHeader' is currently used only by HSTS.  When behind a reverse proxy which handles SSL,
the proxy server will often add this header on the request to inform the app server whether HTTP or HTTPS was used.  While 'X-Forwarded-Proto' is the
defacto-standard (AWS's elastic load balancer does this by default, for example), you can change this is if needed.

## Feedback
Feedback on the source-csp project can be directed to opensource@sourceclear.com.