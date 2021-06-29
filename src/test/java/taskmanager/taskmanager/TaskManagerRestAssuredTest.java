package taskmanager.taskmanager;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.LogConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.io.output.WriterOutputStream;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import taskmanager.junit5.RestAssuredDiskLogRule;
import taskmanager.models.Task;
import taskmanager.models.TaskComment;
import static io.restassured.RestAssured.given;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import java.io.FileWriter;
import java.io.PrintStream;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@ExtendWith(RestAssuredDiskLogRule.class)
public class TaskManagerRestAssuredTest {

    @BeforeAll
    static void initAll() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.baseURI = "http://localhost:9020";
        RestAssured.basePath = "/";
        RequestSpecification requestSpecification = new RequestSpecBuilder().
                //addHeader("jwt", "wrwerwerwe13123123122312").
                addHeader("X-Response-Control", "minified")
                .build();
        RestAssured.requestSpecification = requestSpecification;
        assertNotNull("Runs before all tests");
        try {
            try (FileWriter fileWriter = new FileWriter("logging.txt");
                 PrintStream printStream = new PrintStream(new WriterOutputStream(fileWriter), true)) {

                RestAssured.config = RestAssured.config().logConfig(LogConfig.logConfig().defaultStream(printStream));
            }
        }catch (Exception exp) {
            fail(exp);
        }

        }

    @AfterAll
    static void tearDownAll() {
        assertNotNull("Runs after all tests are run");
    }

    @AfterEach
    void tearDown() {
        assertNotNull("Executed for each test");
    }

    @BeforeEach
    void init() {
        assertNotNull("Executed for each test");
    }

    @Test
    @DisplayName("Asserting Task structure")
    @Tag("smoke")
    void getTaskStructure() {
        Task task = getFirstTask();
        assertNotNull(task, " No  entry seems to exist in Mongo dude! What are you testing?");
        String id = task.getId();

        /** INITIALIZE */
        given()
                .pathParam("id", id)
                .contentType(ContentType.JSON).
        /** EXECUTION */
        when()
                .get("/api/tasks/{id}").
        /** VERIFICAION */
        then()
                .assertThat()
                .body("id",
                        equalTo(id))
                .body("title", equalTo(task.getTitle()))

        ;

    }


    @Test
    @DisplayName("creating new Tasks")
    void createTasks() {

        String id = UUID.randomUUID().toString();;
        Task task = new Task();
        task.setCompleted(false);
        task.setOwner("Praveen");
        task.setCreatedAt(new Date());
        task.setId(id);
        task.setTitle("Review  SpringBoot Framework "+id);
        task.setSquad(asList("Praveen", "Pranav", "Anusha", "Sahana"));
        task.setSubTasks(childTasks());
        task.tags("microservice", "test");
        task.comments(
                TaskComment.builder().author("Praveen").email("pmanvi@outlook.com")
                    .description("why bla bla").build(),

                TaskComment.builder().author("SKP").email("spuvvala@test.com")
                         .creationDate(toDate(LocalDateTime.now().plusHours(1)))
                        .description("because bla is a bla").build(),

                TaskComment.builder().author("Praveen").email("pmanvi@test.com")
                        .creationDate(toDate(LocalDateTime.now().plusHours(2)))
                        .description("where is the proof?").build(),

                TaskComment.builder().author("Anusha").email("anusha@test.com")
                        .creationDate(toDate(LocalDateTime.now().plusHours(3)))
                        .description("Please refer bla").build(),

                TaskComment.builder().author("Praveen").email("pmanvi@test.com")
                        .creationDate(toDate(LocalDateTime.now().plusHours(3)))
                        .description("Ok Got it.").build()

                );

         given()
                .contentType(ContentType.JSON).
         when()
                .body(task).post("/api/tasks")
         ;//.then()
          //      .assertThat()
                //.body("id", equalTo(id))
                //.body("title", equalTo(task.getTitle()))
                //.statusCode(200);
    }

    @Test
    @DisplayName("validate Task conforms with the schema:")
    void jsonValidator() {

        Task task = getFirstTask();
        if (task == null) {
            return;
        }

        given()
                .contentType(ContentType.JSON).
        when()
                .get("/api/tasks/" + task.getId())
       ;// then().assertThat()
                //.body(matchesJsonSchemaInClasspath("task.json"));
    }

    private Date toDate(LocalDateTime ldt) {
        Instant instant = ldt.toInstant(ZoneOffset.UTC);
        return Date.from(instant);
    }


    private Task getFirstTask() {
        Response response = given().contentType(ContentType.JSON).when().get("/api/tasks").thenReturn();
        List tasks = response.jsonPath().get();
        if (tasks.isEmpty()) {
            return null;
        }
        String json = new Gson().toJson(tasks.get(0));
        return new Gson().fromJson(json, Task.class);

    }

    @Test
    @DisplayName("TaskManager List Test")
    @Tag("smoke")
    void getTodosTestParam() {
        getTodsTestParamLocust();
    }

    public Response getTodsTestParamLocust() {
        Task task = getFirstTask();
        assertNotNull(task, " No  entry seems to exist in Mongo dude! What are you testing?");
        String id = task.getId();

        return
                given()
                        .param("id", task.getId()).
                when()
                        .get("/api/tasks/").
                then()
                        .assertThat()
                        .body("id",
                                equalTo(task.getId()))
                        .body("title", equalTo(task.getTitle()))

         .extract().response();
    }

    private List<Task> childTasks() {
        return asList(
                Task.builder().title("Create Docker eco system, Locust & JSAP Services ")
                        .owner("SKP").id(UUID.randomUUID().toString()).completed(true)
                        .squad(asList("PersonA", "PersonB"))
                        .createdAt(new Date())
                        .build(),

                Task.builder().title("Framework enhancing RestAssured for Locust")
                        .owner("Praveen").id(UUID.randomUUID().toString()).completed(true)
                        .squad(asList("MJ"))
                        .createdAt(new Date())
                        .build(),

                Task.builder().title("Product Test suite - Locust enabled")
                        .owner("Sanjeev").id(UUID.randomUUID().toString()).completed(true)
                        .squad(asList("KK", "SKP"))
                        .createdAt(new Date())
                        .build(),

                Task.builder().title("Generate Report - Auto recommend scaling options")
                        .owner("PM").id(UUID.randomUUID().toString()).completed(true)
                        .createdAt(new Date())
                        .squad(asList("SKP", "Praveen"))
                        .build(),

                Task.builder().title("Create presentation on usage of JUnit5 and RestAssured")
                        .owner("Praveen").id(UUID.randomUUID().toString()).completed(false)
                        .squad(asList("KK", "SKP"))
                        .createdAt(new Date())
                        .build()

        );
    }


}
