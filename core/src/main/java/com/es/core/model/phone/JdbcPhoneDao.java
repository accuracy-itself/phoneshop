package com.es.core.model.phone;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.sql.PreparedStatement;

@Component
public class JdbcPhoneDao implements PhoneDao {
    @Resource
    private JdbcTemplate jdbcTemplate;

    private final String QUERY_SELECT_PHONE_BY_ID =
            "select phones.*, " +
                    "colors.id as colorId," +
                    "colors.code as colorCode " +
                    "from phones " +
                    "left join phone2color on phone2color.phoneId = phones.id " +
                    "left join colors on phone2color.colorId = colors.id " +
                    "where phones.id = ? ";

    private final String QUERY_SELECT_AVAILABLE =
            "join stocks on stocks.phoneId = phones.id " +
                    "where phones.price is not null and stocks.stock > stocks.reserved ";
    private final String QUERY_SELECT_AVAILABLE_PHONES_WITH =
            "select phones.*, " +
                    "colors.id as colorId," +
                    "colors.code as colorCode " +
                    "from (select * from phones " +
                    QUERY_SELECT_AVAILABLE;

    private final String QUERY_OFFSET_LIMIT = "offset ? limit ? ) as phones ";

    private final String QUERY_JOIN_COLOR =
            "left join phone2color on phone2color.phoneId = phones.id " +
                    "left join colors on phone2color.colorId = colors.id ";
    private final String QUERY_INSERT_PHONE_2_COLOR =
            "insert into phone2color (phoneId, colorId) " +
                    "values (?, ?) ";

    private final String QUERY_COUNT_AVAILABLE_PHONES =
            "select count(1) from phones " +
                    QUERY_SELECT_AVAILABLE;

    private final String QUERY_SORT_ORDER_BY =
            "order by ";

    private final String QUERY_SEARCH_MODEL =
            " lower(phones.model) like '%?%' ";


    public Optional<Phone> get(final Long key) {
        return jdbcTemplate.query(QUERY_SELECT_PHONE_BY_ID, new PhoneResultSetExtractor(), key).stream()
                .findAny();
    }

    public void save(final Phone phone) {
        SimpleJdbcInsert phoneSimpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("phones");
        Map<String, Object> phoneParameters = new HashMap<>();
        phoneParameters.put("id", phone.getId());
        phoneParameters.put("announced", phone.getAnnounced());
        phoneParameters.put("backCameraMegapixels", phone.getBackCameraMegapixels());
        phoneParameters.put("model", phone.getModel());
        phoneParameters.put("brand", phone.getBrand());
        phoneParameters.put("price", phone.getPrice());
        phoneParameters.put("displaySizeInches", phone.getDisplaySizeInches());
        phoneParameters.put("weightGr", phone.getWeightGr());
        phoneParameters.put("lengthMm", phone.getLengthMm());
        phoneParameters.put("widthMm", phone.getWidthMm());
        phoneParameters.put("heightMm", phone.getHeightMm());
        phoneParameters.put("deviceType", phone.getDeviceType());
        phoneParameters.put("os", phone.getOs());
        phoneParameters.put("displayResolution", phone.getDisplayResolution());
        phoneParameters.put("pixelDensity", phone.getPixelDensity());
        phoneParameters.put("displayTechnology", phone.getDisplayTechnology());
        phoneParameters.put("frontCameraMegapixels", phone.getFrontCameraMegapixels());
        phoneParameters.put("ramGb", phone.getRamGb());
        phoneParameters.put("internalStorageGb", phone.getInternalStorageGb());
        phoneParameters.put("batteryCapacityMah", phone.getBatteryCapacityMah());
        phoneParameters.put("talkTimeHours", phone.getId());
        phoneParameters.put("standByTimeHours", phone.getStandByTimeHours());
        phoneParameters.put("bluetooth", phone.getBluetooth());
        phoneParameters.put("positioning", phone.getPositioning());
        phoneParameters.put("imageUrl", phone.getImageUrl());
        phoneSimpleJdbcInsert.execute(phoneParameters);

        Map<String, Object> phone2colorParameters = new HashMap<>();
        SimpleJdbcInsert phone2colorSimpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("phone2color");
        phone2colorSimpleJdbcInsert.execute(phone2colorParameters);

        jdbcTemplate.batchUpdate(QUERY_INSERT_PHONE_2_COLOR,
                phone.getColors(),
                10,
                (PreparedStatement ps, Color color) -> {
                    ps.setLong(1, phone.getId());
                    ps.setLong(2, color.getId());
                });
    }

    public List<Phone> findAll(String query, SortField sortField, SortOrder sortOrder, int offset, int limit) {
        //String queryPhones =
        StringBuilder queryPhones = new StringBuilder(QUERY_SELECT_AVAILABLE_PHONES_WITH);

        boolean queryEmpty = query == null || query.trim().equals("");
        if (!queryEmpty) {
            queryPhones.append(" and ").append(QUERY_SEARCH_MODEL.replace("?", query));
        }

        if (sortField != null && sortOrder != null) {
            if (sortField == SortField.BRAND || sortField == SortField.MODEL) {
                queryPhones.append(QUERY_SORT_ORDER_BY).append("lower(").append(sortField).append(") ");
            } else {
                queryPhones.append(QUERY_SORT_ORDER_BY).append(sortField).append(" ");
            }

            queryPhones.append(sortOrder).append(" ");
        }

        queryPhones.append(QUERY_OFFSET_LIMIT);
        queryPhones.append(QUERY_JOIN_COLOR);

        return jdbcTemplate.query(queryPhones.toString(), new PhoneResultSetExtractor(), offset, limit);
    }

    @Override
    public int countAvailable(String query) {
        StringBuilder queryPhones = new StringBuilder(QUERY_COUNT_AVAILABLE_PHONES);
        boolean queryEmpty = query == null || query.trim().equals("");
        if (!queryEmpty) {
            queryPhones.append("and " + QUERY_SEARCH_MODEL.replace("?", query));
        }
        return jdbcTemplate.queryForObject(queryPhones.toString(), Integer.class);
    }
}
