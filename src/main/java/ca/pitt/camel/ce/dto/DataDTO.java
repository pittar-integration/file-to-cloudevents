package ca.pitt.camel.ce.dto;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
@CsvRecord(separator = ",", skipField = true)
public class DataDTO {

    @DataField(pos = 1)
    private String name;

    public DataDTO() {
        this.name = "";
    }

    public DataDTO(String name) {
        this.name=name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof DataDTO)) {
            return false;
        }

        DataDTO other = (DataDTO) obj;

        if (null == this.name) {
            return false;
        }

        return this.name.equals(other.getName());
    }

    @Override
    public int hashCode() {
        if (null == this.name) {
            return -1;
        } 
        return this.name.hashCode();
    }

    @Override
    public String toString() {
        return "Name: " + this.name;
    }

}
