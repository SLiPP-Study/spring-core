package bean;

/**
 * Created by hotjoyit on 16. 3. 13.
 */
public class DependentBean extends CommonBean {

  private IndependentBean independentBean;

  @Override
  public String getName() {
    return "Dependent Bean";
  }

  public IndependentBean getIndependentBean() {
    return independentBean;
  }

  public void setIndependentBean(IndependentBean independentBean) {
    this.independentBean = independentBean;
  }
}
