Swagger URL will be: http://localhost:8080/swagger-ui/index.html

we have added swagger doc, 
dependency added is 
	<dependency>
			<groupId>org.springdoc</groupId>
			<artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
			<!-- <version>1.6.14</version> -->
    		<version>2.5.0</version>
		</dependency>
        humare latest version k hisab se


next: since api is secured , so we need to allow swagger api endpoint without token so 
so in securityConfig.java the following permissions are added
 .requestMatchers("/public/**","/swagger-ui/**","/swagger-ui.html", "/v3/api-docs/**").permitAll()


 humne phle itna hi kiya tha 
 .requestMatchers("/public/**","/swagger-ui/**","/swagger-ui.html").permitAll()
 and ye error aaya tha uska reason tha
 🔥 Root Cause (Tumhare setup me)
Tumne security me ye likha hai:
.anyRequest().authenticated()
👉 Swagger UI open ho raha hai
👉 But ye internally call karta hai:
/v3/api-docs/swagger-config
❌ Aur wo blocked hai (401)

isliye finally "/v3/api-docs/** ko bhi pulic kr diya to vo accesss ho gaya bina toke  k.
.requestMatchers("/public/**","/swagger-ui/**","/swagger-ui.html", "/v3/api-docs/**").permitAll()


abhi swageer open ho gaya since humne swagger k endpoint ko public kr diya
but swagger se application wale endpoipnts access nahi kr paa rahe he , kyyuki vo to token based hi rhege.
uske liye swagger se token paas krna pdega ya swagger se vo application wale endpoint ko bina token k alllow krna pdega.
*******************************
issue: swagger se appplication k endpoint access nahi kr paa rahe he 
ab Swagger open ho raha hai, but Swagger se API call (like /api/employees) fail ho raha hai.
Swagger UI → open ✅
But /api/employees → JWT required ❌
✅ Solution (Production Way)

👉 Swagger me JWT token attach karna padega