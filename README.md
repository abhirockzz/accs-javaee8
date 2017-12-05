## Build

- `git clone https://github.com/abhirockzz/accs-javaee8.git`
- `mvn clean install`

The build process will create `accs-javaee8-dist.zip` along with `accs-javaee8.war` in the `target` directory

## Run locally

`java -jar payara-micro.jar --deploy accs-javaee8.war`

## Deploy to Oracle Application Container Cloud

- Install [PSM CLI](https://docs.oracle.com/en/cloud/paas/java-cloud/pscli/using-command-line-interface-1.html)
- execute `psm accs push -n accsjavaee8 -r java -s hourly -m manifest,json -d deployment.json -p target/accs-javaee8-dist.zip`

## For details

- check out the blog - [Run Java EE 8 apps on Oracle Cloud with Payara Micro](https://medium.com/oracledevs/run-java-ee-8-apps-on-oracle-cloud-with-payara-micro-d9b527adaac9)
