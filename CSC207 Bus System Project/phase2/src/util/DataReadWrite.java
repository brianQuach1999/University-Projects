package util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class DataReadWrite<T> {

  private String path;
  private File file;

  public DataReadWrite(String path) throws DataWriteException {

    try {
      this.path = path;

      file = new File(path);
      if (!file.exists()) {
        file.createNewFile();
      }
    } catch (IOException e) {
      throw new DataWriteException();
    }
  }

  public T read() throws DataReadException {

    T data;

    try {
      InputStream file = new FileInputStream(path);
      InputStream buffer = new BufferedInputStream(file);
      ObjectInput input = new ObjectInputStream(buffer);

      //deserialize the Map
      data = (T) input.readObject();
      input.close();
    } catch (Exception e) {
      throw new DataReadException();
    }

    return data;
  }

  public void save(T object) throws DataWriteException {

    try {
      OutputStream file = new FileOutputStream(path);
      OutputStream buffer = new BufferedOutputStream(file);
      ObjectOutput output = new ObjectOutputStream(buffer);

      output.writeObject(object);
      output.close();
    } catch (IOException e) {
      throw new DataWriteException();
    }

  }
}
