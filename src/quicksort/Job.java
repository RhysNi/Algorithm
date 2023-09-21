package quicksort;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Rhys.Ni
 * @version 1.0
 * @date 2023/9/20 2:52 AM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Job {
    private int leftIndex;
    private int rightIndex;
}
