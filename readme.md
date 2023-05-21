<div style="text-align: center">
<h2>Spring Boot Project</h2>
</div>
<article>
A SpringBoot Java API that manages users and persists them in a PostgresDB.
</article>

<h3>Steps to run the project</h3>
<h4 style="text-decoration: underline">Without Docker</h4>
<h4>Requirements</h4>
<ul>
    <li>maven: 3.8.6</li>
    <li>Java: 17</li>
    <li>PostgreSQL</li>
    <li>Docker</li>
</ul>
<p>Step 1: Run below command to start postgres docker container</p>

```
docker run --name postgres_docker -e POSTGRES_PASSWORD=1234 -e POSTGRES_USER=root -e POSTGRES_DB=assignment -p 5432:5432 -d postgres
```

<p>Step 2: Run below command to start spring boot backend</p>

```
mvn spring-boot:run
```

<h4 style="text-decoration: underline">With Docker</h4>
<h4>Requirements</h4>
<ul>
    <li>Docker</li>
</ul>
<p>Run below command to start docker</p>

```
docker-compose up
```

<h3>Api Documentation</h3>
<a href="http://localhost:8080/swagger-ui/index.html">Swagger Api Documentation</a>
