Web service models
==================

## Guidelines

* Do not reference classes from Azure IoT SDK (or other SDKs), but reference them
  in the service layer preferably.
* Maintain the API contract with the corresponding .NET project.
* Ensure datetime values are transfered using UTC timezone.

## Conventions

* Add the "ApiModel" suffix to the models in this folder. This allows to
  distinguish these classes from the classes in the SDK and in the
  service layer.
* Hard code JSON property names using an explicit @JsonProperty attribute
  to avoid breaking the API contract in case of refactoring.
* Use CamelCase for the API property names.
* For DateTime fields use org.joda.time.DateTime.
* Format DateTime fields to UTC with format "yyyy-MM-dd'T'HH:mm:ssZZ".
