/*
 * 
 */

package dyq.dsearch.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorReturn {
    private String result;
    private String reason;
}
