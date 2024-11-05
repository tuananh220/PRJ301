package tags;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ToVietnameseDateTag extends TagSupport {
    private Date value;

    public void setValue(Date value) {
        this.value = value;
    }

    @Override
    public int doStartTag() throws JspException {
        if (value != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("'ngay' dd 'thang' MM 'nam' yyyy");
            String formattedDate = dateFormat.format(value);
            try {
                pageContext.getOut().write(formattedDate);
            } catch (IOException e) {
                throw new JspException("Error in ToVietnameseDateTag", e);
            }
        }
        return SKIP_BODY;
    }
}
