
keytool -genkey -alias Tomcat -keyalg RSA -keysize 1024 -keystore server.jks -validity 365

keytool -list -rfc -keystore server.jks