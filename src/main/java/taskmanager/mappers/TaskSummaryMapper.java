package taskmanager.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import taskmanager.models.Task;
import taskmanager.models.TaskSummary;

@Mapper
public interface TaskSummaryMapper {

    TaskSummaryMapper Mapper = Mappers.getMapper( TaskSummaryMapper.class );

    @Mappings( {
            @Mapping(source = "title", target = "taskDescription")
    })
    TaskSummary toSummary(Task task);

}
