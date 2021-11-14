package today.what_should_i_eat_today.event.event;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DomainEvent {
    private Object domain;
    private Object param;

    protected DomainEvent(Object domain) {
        this.domain = domain;
    }

    protected DomainEvent(Object domain, Object param) {
        this.domain = domain;
        this.param = param;
    }

    public Object getDomain() {
        return this.domain;
    }

    protected Object getParam() {
        return this.param;
    }
}
