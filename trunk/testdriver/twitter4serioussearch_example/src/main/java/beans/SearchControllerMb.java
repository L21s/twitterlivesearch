package beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class SearchControllerMb {
	@Inject
	private SearchModelMb bean;
	
	public String performSearch() {
		return bean.getQuery();
	}
}
