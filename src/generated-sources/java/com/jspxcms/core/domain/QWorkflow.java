package com.jspxcms.core.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWorkflow is a Querydsl query type for Workflow
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QWorkflow extends EntityPathBase<Workflow> {

    private static final long serialVersionUID = 1135479901L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWorkflow workflow = new QWorkflow("workflow");

    public final StringPath description = createString("description");

    public final QWorkflowGroup group;

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath name = createString("name");

    public final NumberPath<Integer> seq = createNumber("seq", Integer.class);

    public final QSite site;

    public final NumberPath<Integer> status = createNumber("status", Integer.class);

    public final ListPath<WorkflowStep, QWorkflowStep> steps = this.<WorkflowStep, QWorkflowStep>createList("steps", WorkflowStep.class, QWorkflowStep.class, PathInits.DIRECT2);

    public QWorkflow(String variable) {
        this(Workflow.class, forVariable(variable), INITS);
    }

    public QWorkflow(Path<? extends Workflow> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWorkflow(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWorkflow(PathMetadata metadata, PathInits inits) {
        this(Workflow.class, metadata, inits);
    }

    public QWorkflow(Class<? extends Workflow> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.group = inits.isInitialized("group") ? new QWorkflowGroup(forProperty("group"), inits.get("group")) : null;
        this.site = inits.isInitialized("site") ? new QSite(forProperty("site"), inits.get("site")) : null;
    }

}

