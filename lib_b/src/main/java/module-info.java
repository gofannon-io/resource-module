module lib_b {
    exports com.example.resource_module.lib_b;

    opens com.example.resource_module.lib_b.opened;
    opens com.example.resource_module.lib_b.dir.opened;

    // shared package & directory
    exports com.example.resource_module.lib_b.shared;
    opens com.example.resource_module.lib_b.shared;
}