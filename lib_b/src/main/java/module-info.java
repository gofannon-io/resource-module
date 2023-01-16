module lib_b {
    exports com.example.resource_module.lib_b;

    opens com.example.resource_module.lib_b.opened;
    opens com.example.resource_module.lib_b.dir.opened;
}