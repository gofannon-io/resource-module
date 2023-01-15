module theapp_a {
    opens  com.example.resource_module.theapp.opened;
    exports com.example.resource_module.theapp;

    opens  com.example.resource_module.theapp.dir.opened;
}