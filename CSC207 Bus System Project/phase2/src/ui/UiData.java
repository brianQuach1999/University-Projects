package ui;

public class UiData<T> {

  private T data;

  public UiData(T data) {
    this.data = data;
  }

  public T data() {
    return this.data;
  }
}
