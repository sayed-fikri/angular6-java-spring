package com.irichment.enrollment.domain.model;


import com.irichment.core.domain.DexMetadata;

import javax.persistence.*;

@Entity(name = "DexEvaluationStatus")
@Table(name = "DEX_EVLN_STTS")
public class DexEvaluationStatusImpl implements DexEvaluationStatus {


    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "SQ_DEX_EVLN_STTS")
    @SequenceGenerator(name = "SQ_DEX_EVLN_STTS", sequenceName = "SQ_DEX_EVLN_STTS", allocationSize = 1)
    private Long id;

    @Column(name = "MIN")
    private Integer min;

    @Column(name = "MAX")
    private Integer max;

    @Column(name = "STATUS")
    private String status;

    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "STATUS_TYPE")
    private DexStatusType statusType;

    @Embedded
    private DexMetadata metadata;

    @ManyToOne(targetEntity = DexEvaluationSchemaImpl.class,
            cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "SCHEMA_ID")
    private DexEvaluationSchema schema;


    @Override
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    @Override
    public Integer getMin() { return min; }

    @Override
    public void setMin(Integer min) { this.min = min; }

    @Override
    public Integer getMax() { return max; }

    @Override
    public void setMax(Integer max) { this.max = max; }

    @Override
    public String getStatus() { return status; }

    @Override
    public void setStatus(String status) { this.status = status; }

    @Override
    public DexStatusType getStatusType() { return statusType; }

    @Override
    public void setStatusType(DexStatusType statusType) { this.statusType = statusType; }

    @Override
    public DexMetadata getMetadata() { return metadata; }

    @Override
    public void setMetadata(DexMetadata metadata) { this.metadata = metadata; }

    @Override
    public DexEvaluationSchema getSchema() { return schema; }

    @Override
    public void setSchema(DexEvaluationSchema schema) { this.schema = schema; }

    @Override
    public Class<?> getInterfaceClass() {
        return DexEvaluationStatus.class;
    }
}
