# SpringBoot MongoDB

This Project helps you to understand Spring-powered, production-grade applications using MongoDB.
It takes an opinionated view of the Spring platform so that new and existing users can quickly get to the bits they need.

Primary goals are:

* Provide a radically faster and widely accessible getting started experience using Mongo Db and Spring Boot.
s

## Installation and Getting Started

The  [reference documentation](https://www.mongodb.com/docs/manual/tutorial/install-mongodb-on-os-x) includes detailed installation instructions of MongoDb server on Mac OS.

To Visualize MongoDb I used  [``Studio 3t``](https://studio3t.com/download)

Here is a quick teaser of a complete Spring Boot application in Java:


```java

@SpringBootApplication
@EnableMongoRepositories
public class NosqlApplication {

	public static void main(String[] args) {
		SpringApplication.run(NosqlApplication.class, args);
	}

}

```

### MongoDB Query
```sql
db.getCollection("student").find({
    $or: [{
        "name": "steve"
    }, {
        "mail": "steve@gmail.com"
    }

    ],
    "name": {
        $in: ["john"]
    }
})
```

This query will find documents in ***student*** collection having given name or mail and name in given parameter

### Usage

* The `StudentController` class, providing static convenience methods that can be used to write a stand-alone Rest API Spring Application.
 ```java

 @RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        //create document
        return studentService.createStudent(student);
    }
}

 ```

* Embedded web applications with a choice of container (Tomcat, Jetty, or Undertow).
* URLs
    * [this document](https://github.com/suyashs52/spring_nosql/blob/master/nosql/src/main/java/com/mongo/nosql/controller/StudentController.java) for more information on Pagination, CRUD Basic Logic.
    * Query on `Pagination` and `Sorting` in MongoDB

      ```sql
        db = db.getSiblingDB("spring");
        db.getCollection("student").find({

        }).sort({ "name": 1.0 }).skip(0).limit(10);


      ```
* After successful Hit the URL
    * `GET` http://localhost:8080/api/student/page/1/size/20
    * `GET` http://localhost:8080/api/student/asc
    *  ![Sorting in ASC Order](https://github.com/suyashs52/spring_nosql/blob/master/nosql/src/main/resources/static/sorting_demo.png?raw=true)
