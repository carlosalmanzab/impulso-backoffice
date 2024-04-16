package com.impulso.impulsobackoffice.departamento.infrastructure.adapter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.impulso.impulsobackoffice.departamento.domain.model.Departamento;
import com.impulso.impulsobackoffice.departamento.domain.model.Municipio;
import com.impulso.impulsobackoffice.departamento.domain.port.out.DepartamentoExternalServicePort;
import com.impulso.impulsobackoffice.departamento.infrastructure.exception.DepartmentDataNotFoundException;
import com.impulso.impulsobackoffice.departamento.infrastructure.exception.DepartmentExternalServiceException;

public class DepartamentoExternalServiceAdapter implements DepartamentoExternalServicePort {

    private final RestTemplate restTemplate;

    public DepartamentoExternalServiceAdapter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Retrieves the list of departments from the API.
     *
     * @return the list of departments
     */
    @Override
    public List<Departamento> getDepartamentos()
            throws DepartmentDataNotFoundException, DepartmentExternalServiceException {
        final String apiUrl = "https://api-colombia.com/api/v1/Department";
        List<Departamento> departamentos = new ArrayList<>();

        try {
            ResponseEntity<DepartmentJson[]> response = restTemplate
                    .getForEntity(apiUrl, DepartmentJson[].class);

            DepartmentJson[] departmentJsons = response.getBody();
            if (departmentJsons == null) {
                throw new DepartmentDataNotFoundException();
            }

            for (DepartmentJson departmentJson : departmentJsons) {
                if (departmentJson != null) {
                    Departamento departamentoBuilded = buildDepartamento(departmentJson, apiUrl);
                    departamentos.add(departamentoBuilded);
                }
            }

            return departamentos.stream().sorted(
                    (d1, d2) -> d1.getNombre().compareToIgnoreCase(d2.getNombre())).toList();
        } catch (Exception e) {
            throw new DepartmentExternalServiceException(e);
        }
    }

    /**
     * Builds and returns a Departamento object based on the provided DepartmentJson
     * and apiUrl.
     *
     * @param departmentJson the DepartmentJson object to build the Departamento
     *                       from
     * @param apiUrl         the base URL for the API
     * @return the constructed Departamento object
     */
    public Departamento buildDepartamento(DepartmentJson departmentJson, String apiUrl) {
        if (departmentJson == null) {
            return null;
        }
        List<Municipio> municipios = buildMunicipioList(apiUrl, (int) departmentJson.id());

        return new Departamento((int) departmentJson.id(), departmentJson.name(), municipios);
    }

    /**
     * Builds a list of Municipio objects by making a GET request to the specified
     * API URL
     * for cities in a given departamentoId.
     *
     * @param apiUrl         the base API URL
     * @param departamentoId the ID of the departamento
     * @return a list of Municipio objects built from the city data retrieved
     */
    public List<Municipio> buildMunicipioList(String apiUrl, int departamentoId) {
        String url = apiUrl + "/" + departamentoId + "/cities";

        List<Municipio> municipios = new ArrayList<>();
        ResponseEntity<CityJson[]> responseCities = restTemplate
                .getForEntity(url, CityJson[].class);

        CityJson[] citiesJsons = responseCities.getBody();
        if (citiesJsons != null) {
            for (CityJson cityJson : citiesJsons) {
                if (cityJson != null) {
                    municipios.add(new Municipio((int) cityJson.id(), cityJson.name()));
                }
            }
        }
        return municipios.stream().sorted((m1, m2) -> m1.getNombre().compareToIgnoreCase(m2.getNombre())).toList();
    }

    /**
     * Get a list of municipalities by department id.
     *
     * @param id the id of the department
     * @return a list of municipalities
     */
    @Override
    public List<Municipio> getMunicipiosByDepartamentoId(int id) throws DepartmentExternalServiceException {
        final String apiUrl = "https://api-colombia.com/api/v1/Department";

        try {
            return buildMunicipioList(apiUrl, id);
        } catch (Exception e) {
            throw new DepartmentExternalServiceException(e);
        }
    }

    private record DepartmentJson(
            float id,
            String name,
            String description,
            float cityCapitalId,
            float municipalities,
            float surface,
            float population,
            String phonePrefix,
            float countryId,
            String cityCapital,
            String country,
            String cities,
            float regionId,
            String region,
            String naturalAreas,
            String maps,
            String indigenousReservations,
            String airports) {
    }

    private record CityJson(
            float id,
            String name,
            String description,
            String surface,
            String population,
            String postalCode,
            float departmentId,
            String department,
            String touristAttractions,
            String presidents,
            String indigenousReservations,
            String airports,
            String radios) {
    }

}
