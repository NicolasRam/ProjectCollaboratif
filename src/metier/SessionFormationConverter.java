package metier;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import beans.LoginBeanProjet;
import modele.SessionFormation;

@FacesConverter(value = "sessionFormationConverter")
public class SessionFormationConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		ValueExpression vex = context.getApplication().getExpressionFactory().createValueExpression(context.getELContext(),
				"#{loginBeanProjet}", LoginBeanProjet.class);

		LoginBeanProjet loginBeanProjet = (LoginBeanProjet) vex.getValue(context.getELContext());
		return loginBeanProjet.getSessionConverter(Integer.valueOf(value));
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object object) {
		return ((SessionFormation)object).getIdSessionFormation().toString();
	}

}
