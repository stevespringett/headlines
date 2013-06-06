## SourceClear - HeadLines
### A collection of security related response headers designed to help increase security for servlet based apps.
===

##Overview
Our HeadLines project is a Java project inspired by Twitter's [SecureHeaders](https://github.com/twitter/secureheaders).  We have a Java implementation which
provides similar functionality to Twitter's Ruby implementation.  While never a guarantee of security, using these headers in your web based applications can help reduce the attack
surface area exposed by your app.  

##Functionality
Here's what our HeadLines implementation covers:

###1) [HTTP Strict Transport Security (HSTS)](https://tools.ietf.org/html/rfc6797)
###2) [X-Frame-Options](https://tools.ietf.org/html/draft-ietf-websec-x-frame-options-00)
###3) [Microsoft's XSS Filter](http://msdn.microsoft.com/en-us/library/dd565647.aspx)
###4) [X-Content-Type Options](http://msdn.microsoft.com/en-us/library/ie/gg622941.aspx)
###5) [CSP](https://developer.mozilla.org/en-US/docs/Security/CSP)
###6) [HttpOnly Cookies](https://www.owasp.org/index.php/HttpOnly)

Most of the functionality is simple to configure.  CSP is the exception, and it may take a period of trial and error to pin down the most secure config that doesn't restrict allowable usage.

##Installation
Maven users can simply use the following dependency:

```xml
<dependency>
  <groupId>com.sourceclear</groupId>
  <artifactId>headlines</artifactId>
  <version>0.1.3</version>    
</dependency>
```

## Setting things up
Once you have dependencies taken care of, setting up HeadLines can be done in two steps:

### 1) Set up the servlet filter
Edit your web.xml file with the following entry:

```xml
<filter>
  <filter-name>headlines</filter-name>
  <filter-class>com.sourceclear.headlines.HeadLinesFilter</filter-class>
</filter>

<filter-mapping>
  <filter-name>headlines</filter-name>
  <url-pattern>*</url-pattern>
</filter-mapping>
```

### 2) Set up a config file (optional).
Without a config file, HeadLines will use a set of restricted default options.  If you want some of the features turned off or wish to fine tune
others you'll want a config file.  Here is a complete config which uses the default settings:

```json

{
  "XContentTypeConfig": {
    "enabled": true
  },
  
  "XFrameOptionsConfig": {
    "enabled": true,
    "value":"DENY"
  },

  "XssProtectionConfig": {
    "enabled": true
  },

  "HstsConfig": {
    "enabled": true,
    "includeSubdomains":true,
    "maxAge":31536000
  },

  "CspConfig": {
    "csp": {
      "default-src":["'self'"]
    },
    "cspReportOnly":{}
  },

  "HttpOnlyConfig": {
    "enabled": true,
    "sessionPatterns": ["JSESSIONID"]
  }
}
```

This file should be saved into WEB-INF/headlines.conf.

Most of the options should be self-explanatory.  The hstsConfig->maxAge option is the length in seconds the HSTS directive should be followed.  The spec
recommends a length of 1 to 2 years.  The defualt is 1 year.  The 'proxyHeader' is currently used only by HSTS.  When behind a reverse proxy which handles SSL,
the proxy server will often add this header on the request to inform the app server whether HTTP or HTTPS was used.  While 'X-Forwarded-Proto' is the
defacto-standard (AWS's elastic load balancer does this by default, for example), you can change this is if needed.

####For CSP
CSP has the bulk of the configuration options.  The following example shows permitted CSP fields:

```json	

  "CspConfig": {
      "default-src":["'none'"],
      "script-src":["'self'"],
      "style-src":["'self'", "fonts.googleapis.com"],
      "img-src":["'self'"],
      "media-src":["'none'"],
      "frame-src":["'none'"],
      "font-src":["themes.googleusercontent.com"],
      "connect-src":["'none'"],
      "report-uri": ["/report/violation"]
  }
```

Each of the source types are defined in the [CSP RFC](http://www.w3.org/TR/2012/CR-CSP-20121115/).  In the above example, media, frames, and connect sources are outlawed
altogether.  Scripts and images may be loaded only from the same host as the loaded page, while CSS and fonts may be loaded from Google (although CSS can also be loaded
from the source host as well).

The report-uri declaration dictates that when the browser determines a loading violation was attempted, it should report this to the url located at /report/violation on
the host server.  This is optional and can often show attack attempts or overly-restricted rules.  Analyzing the report-uri violations is beyond the scope of this project.

####HttpOnly
As discussed in the [OWASP wiki](https://www.owasp.org/index.php/HttpOnly), HttpOnly is a flag in the Set-Cookie response header.  When set, browsers which implement the HttpOnly flag will not allow scripts running within the browser to access the cookie.  When set on session cookies it helps reduce the available vectors for XSS attacks.

HeadLines HttpOnlyConfig accepts a list of regex patterns under the "sessionPatterns" key.  The default is simply "JSESSIONID", but it can be easily modified to use any named pattern to match your sensitive cookies:

```json
  "HttpOnlyConfig": {
    "enabled": true,
    "sessionPatterns": ["JSESSIONID", "myAuthCookie", "auth*"]
  }
  
```

The above settings will match 'JESSIONID', 'myAuthCookie', or any cookie that matches the pattern 'auth*'.  All patterns are considered case insensitive for matching purposes.  When a matching cookie is found, the Set-Cookie header is modified by appending an 'HttpOnly' flag to it.

## Feedback
Feedback on the HeadLines project can be directed to opensource@sourceclear.com.
