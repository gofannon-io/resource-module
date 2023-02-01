module lib_b {
    // Exported package
    exports com.example.resource_module.lib_b;

    // Opened  package
    opens com.example.resource_module.lib_b.opened;

    // Opened directory
    opens com.example.resource_module.lib_b.dir.opened;

    // shared package & directory
    exports com.example.resource_module.lib_b.shared;
    opens com.example.resource_module.lib_b.shared;
}