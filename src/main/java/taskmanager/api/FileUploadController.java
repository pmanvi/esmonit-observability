package taskmanager.api;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/files")
@Api(value = "Rest for file", description = "Rest for File upload example")
@Controller
public class FileUploadController {

    @RequestMapping(value = "/upload", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, method = RequestMethod.POST)
    public void uploadFile(@RequestPart String description, @RequestPart MultipartFile file) {

    }
}