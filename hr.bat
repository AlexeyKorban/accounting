call mvn -B -s settings.xml -DskipTests=true clean package
call call java -Dspring.profiles.active="datajpa,heroku" -DDATABASE_URL="postgres://user:password@localhost:5432/accounting" -jar target/dependency/webapp-runner.jar target/*.war
