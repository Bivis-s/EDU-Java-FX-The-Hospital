package event_handlers;

import db_connection.HospitalDBConnector;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseHandler implements EventHandler<ActionEvent> {
    private Parent parent;
    private HospitalDBConnector dbConnector;

    public BaseHandler(Parent parent, HospitalDBConnector dbConnector) {
        this.parent = parent;
        this.dbConnector = dbConnector;
    }
}
