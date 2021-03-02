# clinked-demo

This demo app provides role-based API (authorization, articles, statistics), storage is in-memory H2 database (with some initial records in data.sql file). 
DB entity model looks following :
![clinked-model](https://user-images.githubusercontent.com/16892576/109687845-52386e80-7b8c-11eb-8ea3-7f883f642dcb.png)

There are 3 user types : USER (can add and list articles), ADMIN (can query article statistics), AUTHOR (can nothing, but used as user for article author). There can be multiple roles assigned to 1 user.

For local testing :
1) git clone repository
2) mvn spring-boot:run
3) Use some of http request sending utility, like Postman or Insomnia:
3.1) Login as user by sending POST request to **http://localhost:8080/api/auth/signin**:
{
	"email": "p.vaskin@mail.ru",
	"password": "password"
}
Then copy-paste accessToken value from response.
3.2) Use this value in futher request(s) as HTTP header "Authorization": "Bearer <insert accessToken here>" :
![Clinked-auth-header](https://user-images.githubusercontent.com/16892576/109690418-ff13eb00-7b8e-11eb-91a2-c284d718fa9e.png)
3.3) Store an article with existing authorId, by sending POST request to **http://localhost:8080/api/articles/create**
{
	"title": "Ctulhu Rising",
	"authorId" : 3,
	"content": "Ctulhu Fthgn!",
	"publishDate": 1614448724775
} 
3.4) List articles by sending GET request to **http://localhost:8080/api/articles/all** . Not-mandatory parameters are page and size(max will be limited by value from application config), e.g **http://localhost:8080/api/articles/all?page=1&size=2**
3.5) Try to access published article count by week on sending GET request to **http://localhost:8080/api/stats/articles/count** and face 403 (Forbidden) response
3.6) Login with admin **http://localhost:8080/api/auth/signin**, by using following credentials:
{
	"email": "v.pupkin@mail.ru",
	"password": "password"
}
3.7) Replace access token in Authorization header for previous request and then retry to get count. Non-mandatory parameter is "period", possible values are [day,week,month,year]. 
![Clinked-stats-month](https://user-images.githubusercontent.com/16892576/109692538-53b86580-7b91-11eb-934d-33d42eaedba2.png)
3.8) If trying different period, like **http://localhost:8080/api/stats/articles/count?period=azaza** error is handled \ wrapped in lv.dev.clinked.demo.infra.ApplicationExceptionHandler and response will contain localized polite error message.

- Project has 100% code coverage for controller | service | util packages (others are covered less for argumented reasons)
- SonarLint has detected some "code smells", however they all are false alarms.
- Validation is performed by out-of-box javax.validation constraints
- (Publish) date formatting is configurable, can be adjusted in application.properties file with values DF_DEFAULT or DF_ISO_8601.
- Article pagination is done by Spring data standartized tools.
- Article content stored as CLOB
- Access to endpoints handled by Spring security mechanisms, based on assigned roles.

Would be grateful for any feedback.
