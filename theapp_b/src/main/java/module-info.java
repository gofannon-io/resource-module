module theapp_b {
    requires org.apache.commons.io;

    opens com.example.resource_module.theapp.opened;
    exports com.example.resource_module.theapp;
}