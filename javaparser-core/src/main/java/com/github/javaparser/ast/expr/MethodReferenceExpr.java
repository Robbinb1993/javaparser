/*
 * Copyright (C) 2007-2010 Júlio Vilmar Gesser.
 * Copyright (C) 2011, 2013-2016 The JavaParser Team.
 *
 * This file is part of JavaParser.
 *
 * JavaParser can be used either under the terms of
 * a) the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * b) the terms of the Apache License
 *
 * You should have received a copy of both licenses in LICENCE.LGPL and
 * LICENCE.APACHE. Please refer to those files for details.
 *
 * JavaParser is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 */
package com.github.javaparser.ast.expr;

import com.github.javaparser.Range;
import com.github.javaparser.ast.AllFieldsConstructor;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.nodeTypes.NodeWithIdentifier;
import com.github.javaparser.ast.nodeTypes.NodeWithTypeArguments;
import com.github.javaparser.ast.observer.ObservableProperty;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.visitor.GenericVisitor;
import com.github.javaparser.ast.visitor.VoidVisitor;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static com.github.javaparser.utils.Utils.assertNonEmpty;

/**
 * Method reference expressions introduced in Java 8 specifically designed to simplify lambda Expressions.
 * Note that the field "identifier", indicating the word to the right of the ::, is not always a method name,
 * it can be "new".
 * <br/>In <code>System.out::println;</code> the scope is System.out and the identifier is "println"
 * <br/><code>(test ? stream.map(String::trim) : stream)::toArray;</code>
 * <br/>In <code>Bar&lt;String>::&lt;Integer>new</code> the String type argument is on the scope, 
 * and the Integer type argument is on this MethodReferenceExpr. 
 *
 * @author Raquel Pau
 */
public class MethodReferenceExpr extends Expression implements NodeWithTypeArguments<MethodReferenceExpr>, NodeWithIdentifier<MethodReferenceExpr> {

    private Expression scope;

    private NodeList<Type> typeArguments;

    private String identifier;

    public MethodReferenceExpr() {
        this(null, new ClassExpr(), null, "empty");
    }

    @AllFieldsConstructor
    public MethodReferenceExpr(Expression scope, NodeList<Type> typeArguments, String identifier) {
        this(null, scope, typeArguments, identifier);
    }

    public MethodReferenceExpr(Range range, Expression scope, NodeList<Type> typeArguments, String identifier) {
        super(range);
        setIdentifier(identifier);
        setScope(scope);
        setTypeArguments(typeArguments);
    }

    @Override
    public <R, A> R accept(GenericVisitor<R, A> v, A arg) {
        return v.visit(this, arg);
    }

    @Override
    public <A> void accept(VoidVisitor<A> v, A arg) {
        v.visit(this, arg);
    }

    public Expression getScope() {
        return scope;
    }

    public MethodReferenceExpr setScope(Expression scope) {
        notifyPropertyChange(ObservableProperty.SCOPE, this.scope, scope);
        this.scope = scope;
        setAsParentNodeOf(this.scope);
        return this;
    }

    @Override
    public Optional<NodeList<Type>> getTypeArguments() {
        return Optional.ofNullable(typeArguments);
    }

    /**
     * Sets the typeArguments
     *
     * @param typeArguments the typeArguments, can be null
     * @return this, the MethodReferenceExpr
     */
    @Override
    public MethodReferenceExpr setTypeArguments(final NodeList<Type> typeArguments) {
        notifyPropertyChange(ObservableProperty.TYPE, this.typeArguments, typeArguments);
        this.typeArguments = typeArguments;
        setAsParentNodeOf(this.typeArguments);
        return this;
    }

    @Override
    public String getIdentifier() {
        return identifier;
    }

    @Override
    public MethodReferenceExpr setIdentifier(String identifier) {
        assertNonEmpty(identifier);
        notifyPropertyChange(ObservableProperty.IDENTIFIER, this.identifier, identifier);
        this.identifier = identifier;
        return this;
    }

    @Override
    public List<NodeList<?>> getNodeLists() {
        return Arrays.asList(getTypeArguments().orElse(null));
    }
}

