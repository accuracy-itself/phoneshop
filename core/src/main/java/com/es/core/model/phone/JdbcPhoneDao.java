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
                    "where phones.id = ?";

    private final String QUERY_SELECT_PHONES_WITH_LIMIT_OFFSET =
            "select phones.*, " +
                    "colors.id as colorId," +
                    "colors.code as colorCode " +
                    "from (select * from phones offset ? limit ? ) as phones " +
                    "left join phone2color on phone2color.phoneId = phones.id " +
                    "left join colors on phone2color.colorId = colors.id";

    private final String QUERY_INSERT_PHONE_2_COLOR =
            "insert into phone2color (phoneId, colorId) " +
                    "values (?, ?)";


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

        Map<String, Object> phone2colorParametres = new HashMap<>();
        SimpleJdbcInsert phone2colorSimpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("phone2color");
        phone2colorSimpleJdbcInsert.execute(phone2colorParametres);

        jdbcTemplate.batchUpdate(QUERY_INSERT_PHONE_2_COLOR,
                phone.getColors(),
                10,
                (PreparedStatement ps, Color color) -> {
                    ps.setLong(1, phone.getId());
                    ps.setLong(2, color.getId());
                });
    }

    public List<Phone> findAll(int offset, int limit) {
        return jdbcTemplate.query(QUERY_SELECT_PHONES_WITH_LIMIT_OFFSET, new PhoneResultSetExtractor(), offset, limit);
    }
}
