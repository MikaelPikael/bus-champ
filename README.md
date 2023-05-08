### Bus Champ


### Build

#### Build backend image
```
cd backend && ./gradlew build
```
```
docker build -t buschamp/buschamp-be:0.0.1 .
```
#### Build frontend image
```
cd frontend
```
```[docker-compose.yml](docker-compose.yml)
docker build -t buschamp/buschamp-fe:0.0.1 .
```

### How to run  
```
TRAFIKLAB_API_KEY=<TrafikLab API key> docker compose up
```

Open [http://localhost:3000/](http://localhost:3000/) 

### References

- https://www.trafiklab.se/api/trafiklab-apis/sl/stops-and-lines-2/
- https://spring.io/guides/topicals/spring-boot-docker/
- https://github.com/luizgustavocosta/16-bits-zero-to-hero/
- https://www.material-tailwind.com/docs/react/installation
### License
<hr>

+ [MIT](https://choosealicense.com/licenses/mit/)
