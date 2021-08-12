/*
 * 
 */

package dyq.dsearch.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
public class Word {

    private String name;
    @EqualsAndHashCode.Exclude
    private float weight;
}
