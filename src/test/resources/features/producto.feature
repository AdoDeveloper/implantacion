Feature: Gestión de Productos

  Scenario: Crear un nuevo producto
    Given que estoy en la página de creación de productos
    When ingreso los datos del producto y envío el formulario
    Then el producto debe ser guardado y redirigido a la lista
